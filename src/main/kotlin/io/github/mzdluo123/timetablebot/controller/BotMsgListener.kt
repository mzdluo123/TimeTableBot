package io.github.mzdluo123.timetablebot.controller


import io.github.mzdluo123.timetablebot.bots.BotsManager
import io.github.mzdluo123.timetablebot.config.AppConfig
import io.github.mzdluo123.timetablebot.gen.timetable.tables.User.USER
import io.github.mzdluo123.timetablebot.gen.timetable.tables.Classroom
import io.github.mzdluo123.timetablebot.gen.timetable.tables.Course
import io.github.mzdluo123.timetablebot.gen.timetable.tables.CourseTime
import io.github.mzdluo123.timetablebot.gen.timetable.tables.UserCourse
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
import net.mamoe.mirai.message.data.Message
import net.mamoe.mirai.message.data.PlainText
import org.jetbrains.kotlinx.serialization.compiler.backend.jvm.INT
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
            case("select", "查询下节课") {
                queryNextClass(it, user.id)
            }
            case("3", "异常测试") {
                throw IllegalAccessError("2333")
            }
            nextRoute("admin", "管理中心", ::admin)
            default {
                reply(PlainText(AppConfig.getInstance().help))
                reply(PlainText(generateHelp()))
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

        route.default {

            reply(PlainText(route.generateHelp()))
        }
    }

    private suspend fun FriendMessageEvent.queryNextClass(msg: List<String>?, userId: Int) {
        var nextClass = nextClassIndex()
        val week = week()
        var dayOfWeek = dayOfWeek()
        if (nextClass == Int.MAX_VALUE) {
            dayOfWeek++
            nextClass = 1
        }

        val course = dbCtx {
            return@dbCtx it.select(
                Course.COURSE.NAME,
                Course.COURSE.TEACHER,
                Classroom.CLASSROOM.LOCATION,
                Course.COURSE.SCORE,
                Course.COURSE.WEEK_PERIOD,
                Course.COURSE.PERIOD
            )
                .from(
                    UserCourse.USER_COURSE.innerJoin(USER).on(
                        UserCourse.USER_COURSE.USER.eq(
                            USER.ID
                        )
                    )
                        .innerJoin(Course.COURSE).on(UserCourse.USER_COURSE.COURSE.eq(Course.COURSE.ID))
                        .innerJoin(CourseTime.COURSE_TIME)
                        .on(CourseTime.COURSE_TIME.COURSE.eq(Course.COURSE.ID))
                        .innerJoin(Classroom.CLASSROOM)
                        .on(CourseTime.COURSE_TIME.CLASS_ROOM.eq(Classroom.CLASSROOM.ID))
                )
                .where(
                    CourseTime.COURSE_TIME.WEEK.eq(week.toByte())
                        .and(CourseTime.COURSE_TIME.DAY_OF_WEEK.eq(dayOfWeek.toByte()))
                        .and(USER.ENABLE.eq(1))
                        .and(USER.ID.eq(userId))
                        .and(CourseTime.COURSE_TIME.START_TIME.eq(nextClass.toByte()))
                )
                .groupBy(USER.ID).fetchOne()
        }
        val msg = if (course != null){
            buildString {
                append("您好!接下来是第${nextClass}节课\n")
                append(
                    "${course.component1()}，在${course.getValue(Classroom.CLASSROOM.LOCATION)}，${
                        course.getValue(Course.COURSE.SCORE)
                    }个学分"
                )
            }
        }else{
            "没有课了(●ˇ∀ˇ●)"
        }
        reply(PlainText(msg))

    }

}

