package io.github.mzdluo123.timetablebot.controller


import io.github.mzdluo123.timetablebot.BuildConfig
import io.github.mzdluo123.timetablebot.bots.BotsManager
import io.github.mzdluo123.timetablebot.config.AppConfig
import io.github.mzdluo123.timetablebot.gen.timetable.tables.Classroom.CLASSROOM
import io.github.mzdluo123.timetablebot.gen.timetable.tables.Course.COURSE
import io.github.mzdluo123.timetablebot.gen.timetable.tables.CourseTime.COURSE_TIME
import io.github.mzdluo123.timetablebot.gen.timetable.tables.User.USER
import io.github.mzdluo123.timetablebot.gen.timetable.tables.UserCourse.USER_COURSE
import io.github.mzdluo123.timetablebot.gen.timetable.tables.daos.UserDao
import io.github.mzdluo123.timetablebot.gen.timetable.tables.pojos.User
import io.github.mzdluo123.timetablebot.route.CommandRouter
import io.github.mzdluo123.timetablebot.route.cmdArg
import io.github.mzdluo123.timetablebot.route.requireAdminPermission
import io.github.mzdluo123.timetablebot.route.route
import io.github.mzdluo123.timetablebot.schoolservice.getRestaurant
import io.github.mzdluo123.timetablebot.schoolservice.getSchoolNetInterfaceInfo
import io.github.mzdluo123.timetablebot.task.SyncRequest
import io.github.mzdluo123.timetablebot.task.SyncTask
import io.github.mzdluo123.timetablebot.utils.*
import net.mamoe.mirai.event.EventHandler
import net.mamoe.mirai.message.FriendMessageEvent
import net.mamoe.mirai.message.MessageEvent
import net.mamoe.mirai.message.data.PlainText
import okhttp3.internal.format
import org.jooq.Record10
import org.jooq.Record7
import org.jooq.Record9
import org.jooq.Result
import java.time.LocalDateTime


class BotMsgListener : BaseListeners() {
    private val userDao = createDao(UserDao::class)

    @EventHandler
    suspend fun FriendMessageEvent.onEvent() {
        val user = userDao.fetchOneByAccount(sender.id)
        route(prefix = "", delimiter = " ") {
            exception { throwable ->
                PlainText(throwable.toString())
            }
            case("开始", "快速开始使用") {
                getStart(it, user)
            }
            case("init", "设置学号") {
                setUserId(it, user)
            }
            case("sync", "从教务系统同步课程表") {
                syncTimeTable(user, it)
            }
            case("td", "退订自动推送服务") {
                td(user)
            }

            case("dy", "订阅自动推送服务") {
                dy(user)
            }
            case("next", "查询下节课") {
                nextClass(user)
            }
            case("食堂", "查看食堂实时就餐情况") {
                restaurant()
            }
            case("bug反馈", "将bug反馈给开发者，帮助我们进行完善") {
                val arg: String by cmdArg(0, "bug", it)
                for (u in AppConfig.getInstance().admin) {
                    bot.getFriend(u).sendMessage("来自用户${user.account}的反馈:" + arg)
                }
                reply("您反馈的问题我们已经收到，如果您还有疑问，请联系管理员")
            }
            case("今日课表", "获取今天的所有课程") {
                todayTimeTable(user)
            }
            case("本周课表", "查询本周某一天的课程表") {
                val day: Int by cmdArg(0, "星期几", it)
                reply(getTimeTableMsg(week(), day, user))
            }
            case("clean", "清除您的课程表") {
                cleanTimeTable(it, user)
            }
            case("校园网", "查看校园网状态信息") {
                TTBHttpClient().use { client ->
                    val state = getSchoolNetInterfaceInfo(client)
                    val msg = state?.joinToString(separator = "\n") {
                        """
                            ${it.name}
                            使用量:${format("%.2f", ((it.upStream + it.downStream) / it.maxBandWidth) * 100)}%
                            ***********
                        """.trimIndent()
                    } ?: "暂无信息"
                    reply(msg)
                }

            }
            case("help", "指令菜单") {
                reply(PlainText("TimeTableBot ${BuildConfig.VERSION} build ${timeToStr(BuildConfig.BUILD_UNIXTIME)}\n${generateHelp()}"))
            }
            nextRoute("admin", "管理中心", ::admin)
            default {
                reply(PlainText(AppConfig.getInstance().help))
            }

        }

    }

    private suspend fun FriendMessageEvent.cleanTimeTable(
        it: List<String>?,

        user: User
    ) {
        val confirm: Boolean by cmdArg(0, "您确定要清除您的课程表?", it)
        if (confirm) {
            val courses = dbCtx {
                it.delete(USER_COURSE).where(USER_COURSE.USER.eq(user.id)).execute()
            }
            reply("删除了${courses}条记录")
        } else {
            reply("已取消")
        }
    }

    private suspend fun FriendMessageEvent.todayTimeTable(user: User?) {
        if (user == null) {
            reply("您没有创建账号，请使用init创建账户")
            return
        }
        reply(getTimeTableMsg(week(), dayOfWeek(), user))
    }

    private fun getTimeTableMsg(week: Int, dayOfWeek: Int, user: User): String {
        val course = searchTodayClass(week, dayOfWeek, user)
        return if (course != null && course.size >= 1) {
            course.joinToString(separator = "\n") {
                """
    ${it.component1()}
    ${it.component3()} 
    时间：${
                    AppConfig.getInstance().classTime[it.component7().toInt() - 1]
                } (第${it.component7()}节)                             
    --------------
    """.trimIndent()
            }
        } else {
            "您今日没有课哦~"
        }
    }

    private suspend fun FriendMessageEvent.restaurant() {
        TTBHttpClient().use { client ->
            val restaurant = getRestaurant(client)
            val msg = buildString {
                for (i in restaurant.xAxis.indices) {
                    append(restaurant.xAxis[i])
                    append(" ")
                    append(restaurant.data[0][i])
                    append("\n")
                }
            }
            reply(msg)
        }

    }

    private suspend fun FriendMessageEvent.nextClass(user: User?) {
        if (user == null) {
            reply("您没有创建账号，请使用init创建账户")
            return
        }
        reply(nextClassMsg(user))
    }

    private suspend fun FriendMessageEvent.dy(user: User?) {
        if (user == null) {
            reply("您没有创建账号，请使用init创建账户")
            return
        }
        if (user.enable == 0.toByte()) {
            userDao.update(user.apply {
                enable = 1.toByte()
            })
            reply("订阅启用成功！")
        } else {
            reply("您已订阅自动推送服务")
        }
    }

    private suspend fun FriendMessageEvent.td(user: User?) {
        if (user == null) {
            reply("您没有创建账号，请使用init创建账户")
            return
        }
        if (user.enable == 1.toByte()) {
            userDao.update(user.apply {
                enable = 0.toByte()
            })
            reply("退订成功")
        } else {
            reply("您已退订自动推送服务")
        }
    }

    private suspend fun FriendMessageEvent.syncTimeTable(
        user: User?,
        it: List<String>?
    ) {
        if (user == null) {
            reply("您没有创建账号，请使用init创建账户")
            return
        }
        val arg: String by cmdArg(0, "密码", it)
        SyncTask.requestSync(SyncRequest(user.id, arg))
        reply("我们将在后台刷新您的课程表，完成后会向你发送信息，请稍后\n同步较慢，请勿重复提交")
    }

    private suspend fun FriendMessageEvent.setUserId(
        it: List<String>?,
        user: User?
    ) {
        val arg2: Long by cmdArg(0, "学号", it)
        updateUser(user, this, arg2)
        reply("设置学号成功")
    }

    private suspend fun FriendMessageEvent.getStart(
        it: List<String>?,
        user: User?
    ) {
        val arg1: Long by cmdArg(0, "学号", it)
        val arg2: String by cmdArg(1, "登录密码", it)
        reply("现在请输入你的学号")
        val newUser = updateUser(user, this, arg1)
        reply("现在请输入你的密码（统一认证系统的密码或是教务处的密码）")
        SyncTask.requestSync(SyncRequest(newUser.id, arg2))
        reply("我们将在后台刷新您的课程表，完成后会向你发送信息，请稍后\n同步较慢，请勿重复提交")
    }

    private fun updateUser(
        user: User?,
        event: MessageEvent,
        studentId: Long
    ): User {
        return if (user != null) {
            userDao.update(user.apply {
                this.studentId = studentId
            })
            user
        } else {
            val user = User().apply {
                account = event.sender.id
                bot = event.bot.id
                joinTime = LocalDateTime.now()
                enable = 0.toByte()
                this.studentId = studentId
            }
            userDao.insert(user)
            user
        }
    }

    private suspend fun admin(route: CommandRouter<FriendMessageEvent>) {
        route.exception {
            PlainText(it.toString())
        }
        route.event.requireAdminPermission()
        route.case("reload", "重载帮助") {
            AppConfig.loadHelp()
            reply("帮助重载成功!")
        }
        route.case("send", "广播消息到所有用户") {
            val msg: String by cmdArg(0, "要广播的消息", it)
            val users = dbCtx { it.select(USER.ACCOUNT, USER.BOT).from(USER).fetch() }
            for (user in users) {
                BotsManager.sendMsg(
                    user.component2(),
                    user.component1(),
                    PlainText("$msg\n(此消息由管理员发送，请勿回复，如有问题请联系管理员)")
                )
            }
        }
        route.case("state", "获取运行状态") {
            val msg = buildString {
                append("TimeTableBot ${BuildConfig.VERSION} build ${timeToStr(BuildConfig.BUILD_UNIXTIME)}\n")
                val totalUser = dbCtx {
                    it.fetchCount(USER)
                }
                append("用户数:${totalUser}\n")
                val validUser = dbCtx {
                    it.fetchCount(USER, USER.NAME.isNotNull)
                }
                append("有效用户:${validUser}\n")
                append("下节课:${nextClassIndex()}\n")
                val courses = dbCtx {
                    it.fetchCount(COURSE)
                }
                append("记录的课程数量:${courses}")
            }
            reply(msg)
        }
        route.case("info", "系统负载") {
            reply(getSystemInfo())
        }
        route.default {

            reply(PlainText(route.generateHelp()))
        }
    }
}

fun searchTodayClass(
    week: Int,
    dayOfWeek: Int,
    user: User
): Result<Record7<String, String, String, Double, Byte, Byte, Byte>>? {
    val course = dbCtx {
        return@dbCtx it.select(
            COURSE.NAME,
            COURSE.TEACHER,
            CLASSROOM.LOCATION,
            COURSE.SCORE,
            COURSE.WEEK_PERIOD,
            COURSE.PERIOD,
            COURSE_TIME.START_TIME
        )
            .from(
                USER_COURSE.innerJoin(USER).on(USER_COURSE.USER.eq(USER.ID))
                    .innerJoin(COURSE).on(USER_COURSE.COURSE.eq(COURSE.ID))
                    .innerJoin(COURSE_TIME).on(COURSE_TIME.COURSE.eq(COURSE.ID))
                    .innerJoin(CLASSROOM).on(COURSE_TIME.CLASS_ROOM.eq(CLASSROOM.ID))

            )
            .where(
                COURSE_TIME.WEEK.eq(week.toByte())
                    .and(USER.ID.eq(user.id))
                    .and(COURSE_TIME.DAY_OF_WEEK.eq(dayOfWeek.toByte()))
            )
            .orderBy(
                COURSE_TIME.START_TIME
            )
            .fetch()
    }
    return course
}

fun searchNextClass(
    week: Int,
    dayOfWeek: Int,
    user: User
): Record10<String, Int, Long, Byte, Byte, Byte, String, Int, Double, String>? {
    if (nextClassIndex() == Int.MAX_VALUE) return null
    val cource = dbCtx {
        return@dbCtx it.select(
            COURSE.NAME,
            USER.ID,
            USER.ACCOUNT,
            COURSE_TIME.DAY_OF_WEEK,
            COURSE_TIME.WEEK,
            COURSE_TIME.START_TIME,
            USER.NAME,
            COURSE_TIME.CLASS_ROOM,
            COURSE.SCORE,
            CLASSROOM.LOCATION
        )
            .from(
                COURSE
                    .innerJoin(USER_COURSE).on(COURSE.ID.eq(USER_COURSE.COURSE))
                    .innerJoin(USER).on(USER.ID.eq(USER_COURSE.USER))
                    .innerJoin(COURSE_TIME).on(COURSE.ID.eq(COURSE_TIME.COURSE))
                    .innerJoin(CLASSROOM).on(COURSE_TIME.CLASS_ROOM.eq(CLASSROOM.ID))
            )
            .where(
                COURSE_TIME.WEEK.eq(week.toByte())
                    .and(USER.ID.eq(user.id))
                    .and(COURSE_TIME.DAY_OF_WEEK.eq(dayOfWeek.toByte()))
                    .and(COURSE_TIME.START_TIME.ge(nextClassIndex().toByte())),
            )
            .orderBy(COURSE_TIME.START_TIME)
            .limit(1)
            .fetchOne()
    }
    return cource
}

fun searchTomorrowNextClass(
    week: Int,
    dayOfWeek: Int,
    user: User
): Record9<String, Int, Long, Byte, Byte, Byte, Double, String, String>? {
    val cource = dbCtx {
        return@dbCtx it.select(
            COURSE.NAME,
            USER.ID,
            USER.ACCOUNT,
            COURSE_TIME.DAY_OF_WEEK,
            COURSE_TIME.WEEK,
            COURSE_TIME.START_TIME,
            COURSE.SCORE,
            USER.NAME,
            CLASSROOM.LOCATION
        )
            .from(
                COURSE
                    .innerJoin(USER_COURSE).on(COURSE.ID.eq(USER_COURSE.COURSE))
                    .innerJoin(USER).on(USER.ID.eq(USER_COURSE.USER))
                    .innerJoin(COURSE_TIME).on(COURSE.ID.eq(COURSE_TIME.COURSE))
                    .innerJoin(CLASSROOM).on(COURSE_TIME.CLASS_ROOM.eq(CLASSROOM.ID))
            )
            .where(
                COURSE_TIME.WEEK.eq(week.toByte())
                    .and(USER.ID.eq(user.id))
                    .and(COURSE_TIME.DAY_OF_WEEK.eq(dayOfWeek.toByte()))
            )
            .orderBy(COURSE_TIME.START_TIME)
            .limit(1)
            .fetchOne()
    }

    return cource
}

fun nextClassMsg(user: User): String {
    var dayOfWeek = dayOfWeek()
    var week = week()
    val course = searchNextClass(week, dayOfWeek, user)
    return if (course != null) {
        buildString {
            append("您好!接下来是第${course.getValue(COURSE_TIME.START_TIME)}节课，上课时间${AppConfig.getInstance().classTime[course.component6() - 1]}\n")
            append(
                "${course.getValue(COURSE.NAME)}，在${course.getValue(CLASSROOM.LOCATION)}，${
                    course.getValue(
                        COURSE.SCORE
                    )
                }个学分"
            )
        }
    } else {
        //查询第二天的课表
        buildString {

            if (dayOfWeek == 7) {
                dayOfWeek = 1
                week++
            } else {
                dayOfWeek++
            }
            val course = searchTomorrowNextClass(week, dayOfWeek, user)
            if (course != null) {
                append(
                    "您今日已无课，接下来是明天的第${course.component6()}节课," +
                            "上课时间${AppConfig.getInstance().classTime[course.component6().toInt() - 1]}\n"
                )
                append(
                    "${course.getValue(COURSE.NAME)}，在${course.getValue(CLASSROOM.LOCATION)}，${
                        course.getValue(
                            COURSE.SCORE
                        )
                    }个学分"
                )
            } else {
                append("您暂时没有课了哦(*/ω＼*)")
            }
        }
    }
}
