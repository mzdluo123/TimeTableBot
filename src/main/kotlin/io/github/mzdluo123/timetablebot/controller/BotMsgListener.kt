package io.github.mzdluo123.timetablebot.controller

import io.github.mzdluo123.timetablebot.controller.BaseListeners



import io.github.mzdluo123.timetablebot.BuildConfig
import io.github.mzdluo123.timetablebot.bots.BotsManager
import io.github.mzdluo123.timetablebot.config.AppConfig
import io.github.mzdluo123.timetablebot.gen.timetable.tables.Classroom.CLASSROOM
import io.github.mzdluo123.timetablebot.gen.timetable.tables.Course
import io.github.mzdluo123.timetablebot.gen.timetable.tables.Course.COURSE
import io.github.mzdluo123.timetablebot.gen.timetable.tables.CourseTime
import io.github.mzdluo123.timetablebot.gen.timetable.tables.CourseTime.COURSE_TIME
import io.github.mzdluo123.timetablebot.gen.timetable.tables.User.USER
import io.github.mzdluo123.timetablebot.gen.timetable.tables.UserCourse
import io.github.mzdluo123.timetablebot.gen.timetable.tables.UserCourse.USER_COURSE
import io.github.mzdluo123.timetablebot.gen.timetable.tables.daos.UserDao
import io.github.mzdluo123.timetablebot.gen.timetable.tables.pojos.User
import io.github.mzdluo123.timetablebot.route.CommandRouter
import io.github.mzdluo123.timetablebot.route.cmdArg
import io.github.mzdluo123.timetablebot.route.requireAdminPermission
import io.github.mzdluo123.timetablebot.route.route
import io.github.mzdluo123.timetablebot.task.SyncRequest
import io.github.mzdluo123.timetablebot.task.SyncTask
import io.github.mzdluo123.timetablebot.utils.*
import net.mamoe.mirai.event.EventHandler
import net.mamoe.mirai.message.FriendMessageEvent
import net.mamoe.mirai.message.data.PlainText
import org.jooq.*
import java.lang.Byte.parseByte
import java.time.LocalDateTime
import java.time.LocalTime


class BotMsgListener : BaseListeners() {
    private val userDao = createDao(UserDao::class)

    @EventHandler
    suspend fun FriendMessageEvent.onEvent() {
        val user = userDao.fetchOneByAccount(sender.id)
        route(prefix = "", delimiter = " ") {
            exception { throwable ->
                PlainText(throwable.toString())
            }
            case("init", "设置学号") {
                val arg2: Int by cmdArg(0, "学号", it)
                if (user != null) {
                    userDao.update(user.apply {
                        studentId = arg2
                    })
                    reply(PlainText("设置学号成功"))
                } else {
                    userDao.insert(User().apply {
                        account = sender.id
                        bot = sender.bot.id
                        joinTime = LocalDateTime.now()
                        enable = 1.toByte()
                        studentId = arg2
                    })
                    reply(PlainText("创建账号成功"))
                }
            }
            case("sync", "从教务系统同步课程表") {

                if (user == null) {
                    reply("你没有创建账号，请使用init创建账户")
                    return@case
                }
                val arg: String by cmdArg(0, "密码", it)
                SyncTask.requestSync(SyncRequest(user.id, arg))
                reply("我们将在后台刷新你的课程表，完成后会向你发送信息，请稍后\n同步较慢，请勿重复提交")

            }
            case("next", "查询下节课") {
                val nextClass = nextClass(user)
                reply(nextClass)
            }
            case("bug反馈", "将bug反馈给开发者，帮助我们进行完善") {
                val arg: String by cmdArg(0, "bug", it)

                for (u in AppConfig.getInstance().admin) {
                    bot.getFriend(u).sendMessage("来自用户${user.account}的反馈:" + arg)
                }
                reply("您反馈的问题我们已经收到，如果您还有疑问，请联系管理员")
            }
            case("今日课表","获取今天的所有课程"){
                val course= searchTodayClass(dayOfWeek(),user)
                var msg:String="您今日没有课哦~"
                if (course!=null && course.size >=1) {
                    msg=""
                    for (i in course) {
                        msg += ("课程：${i.component1()}\n" +
                                "地点：${i.component3()}\n" +
                                "时间：${AppConfig.getInstance().classTime.get(i.component7().toInt()-1)}\n--------------\n")
                    }
                }
                reply(msg)
            }
            nextRoute("admin", "管理中心", ::admin)
            default {
                reply(PlainText(AppConfig.getInstance().help))
                reply(PlainText("TimeTableBot ${BuildConfig.VERSION} build ${timeToStr(BuildConfig.BUILD_UNIXTIME)}\n${generateHelp()}"))
            }

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
            val users = dbCtx { it.select(USER.ACCOUNT, USER.BOT).from(USER).where(USER.ENABLE.eq(1)).fetch() }
            for (user in users) {
                BotsManager.sendMsg(
                    user.component2(),
                    user.component1(),
                    PlainText("$msg\n(此消息由管理员发送，请勿回复，如有问题请联系管理员)")
                )
            }
        }
        route.default {

            reply(PlainText(route.generateHelp()))
        }
    }
}
fun searchTodayClass(week: Int,user: User): Result<Record7<String, String, String, Double, Byte, Byte, Byte>>? {
    val course= dbCtx {
        return@dbCtx it.select(
                Course.COURSE.NAME,
                Course.COURSE.TEACHER,
                CLASSROOM.LOCATION,
                Course.COURSE.SCORE,
                Course.COURSE.WEEK_PERIOD,
                Course.COURSE.PERIOD,
                CourseTime.COURSE_TIME.START_TIME
        )
                .from(
                        UserCourse.USER_COURSE.innerJoin(io.github.mzdluo123.timetablebot.gen.timetable.tables.User.USER).on(UserCourse.USER_COURSE.USER.eq(io.github.mzdluo123.timetablebot.gen.timetable.tables.User.USER.ID))
                                .innerJoin(Course.COURSE).on(UserCourse.USER_COURSE.COURSE.eq(Course.COURSE.ID))
                                .innerJoin(CourseTime.COURSE_TIME).on(CourseTime.COURSE_TIME.COURSE.eq(Course.COURSE.ID))
                                .innerJoin(CLASSROOM).on(CourseTime.COURSE_TIME.CLASS_ROOM.eq(CLASSROOM.ID))

                )
                .where(
                        CourseTime.COURSE_TIME.WEEK.eq(week.toByte())
                                .and(io.github.mzdluo123.timetablebot.gen.timetable.tables.User.USER.ID.eq(user.id))
                                .and(CourseTime.COURSE_TIME.DAY_OF_WEEK.eq(week.toByte()))
                )
                .orderBy(
                        CourseTime.COURSE_TIME.START_TIME
                )
                .fetch()
    }
    return course
}
fun searchNextClass(week: Int, user: User, now: LocalTime): Record10<String, Int, Long, Byte, Byte, Byte, String, Int, Double, String>? {
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
                    .and(COURSE_TIME.DAY_OF_WEEK.eq(week.toByte()))
                    .and(COURSE_TIME.START_TIME.ge(nextClassIndex().toByte())),

            )
            .groupBy(USER.ID).fetchOne()
    }
    return cource
}
fun searchTomorrowNextClass(week: Int, user: User, now: LocalTime): Record9<String, Int, Long, Byte, Byte, Byte, Double, String, String>? {
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
                                .and(COURSE_TIME.DAY_OF_WEEK.eq(week.toByte()))
                )
                .groupBy(USER.ID).fetchOne()
    }
    return cource
}
