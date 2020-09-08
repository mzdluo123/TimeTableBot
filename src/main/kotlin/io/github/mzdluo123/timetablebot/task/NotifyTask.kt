package io.github.mzdluo123.timetablebot.task

import io.github.mzdluo123.timetablebot.appJob
import io.github.mzdluo123.timetablebot.bots.BotsManager
import io.github.mzdluo123.timetablebot.config.AppConfig
import io.github.mzdluo123.timetablebot.gen.timetable.tables.Classroom.CLASSROOM
import io.github.mzdluo123.timetablebot.gen.timetable.tables.Course.COURSE
import io.github.mzdluo123.timetablebot.gen.timetable.tables.CourseTime.COURSE_TIME
import io.github.mzdluo123.timetablebot.gen.timetable.tables.User.USER
import io.github.mzdluo123.timetablebot.gen.timetable.tables.UserCourse.USER_COURSE
import io.github.mzdluo123.timetablebot.utils.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import net.mamoe.mirai.message.data.PlainText
import org.quartz.Job
import org.quartz.JobExecutionContext
import kotlin.coroutines.CoroutineContext

class NotifyTask : Job, CoroutineScope {
    private val logger = logger()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + SupervisorJob(appJob)

    override fun execute(context: JobExecutionContext?) {
        launch { work() }
    }

    suspend fun work() {
        val nextClass = nextClassIndex()
        val week = week()
        val dayOfWeek = dayOfWeek()
        if (nextClass == Int.MAX_VALUE) {
            logger.info("无需报课")
            return
        }
        logger.info("开始报课,下一节：${nextClass}")
        // 获取有课的用户
        val users = dbCtx {
            return@dbCtx it.select(USER.ID, USER.ACCOUNT, USER.BOT)
                .from(
                    USER_COURSE.innerJoin(USER).on(USER_COURSE.USER.eq(USER.ID))
                        .innerJoin(COURSE).on(USER_COURSE.COURSE.eq(COURSE.ID))
                        .innerJoin(COURSE_TIME).on(COURSE_TIME.COURSE.eq(COURSE.ID))
                        .innerJoin(CLASSROOM).on(COURSE_TIME.CLASS_ROOM.eq(CLASSROOM.ID))
                )
                .where(
                    COURSE_TIME.WEEK.eq(week.toByte())
                        .and(COURSE_TIME.DAY_OF_WEEK.eq(dayOfWeek.toByte()))
                        .and(USER.ENABLE.eq(1))
                        .and(COURSE_TIME.START_TIME.eq(nextClass.toByte()))
                )
                .groupBy(USER.ID).fetch()
        }
        logger.info("需要对${users.size}个用户发送信息")
        for (u in users) {
            val userId = u.getValue(USER.ID)
            val account = u.getValue(USER.ACCOUNT)
            val bot = u.getValue(USER.BOT)
            try {
                val course = dbCtx {
                    return@dbCtx it.select(
                        COURSE.NAME,
                        COURSE.TEACHER,
                        CLASSROOM.LOCATION,
                        COURSE.SCORE,
                        COURSE.WEEK_PERIOD,
                        COURSE.PERIOD
                    )
                        .from(
                            USER_COURSE.innerJoin(USER).on(USER_COURSE.USER.eq(USER.ID))
                                .innerJoin(COURSE).on(USER_COURSE.COURSE.eq(COURSE.ID))
                                .innerJoin(COURSE_TIME).on(COURSE_TIME.COURSE.eq(COURSE.ID))
                                .innerJoin(CLASSROOM).on(COURSE_TIME.CLASS_ROOM.eq(CLASSROOM.ID))
                        )
                        .where(
                            COURSE_TIME.WEEK.eq(week.toByte())
                                .and(COURSE_TIME.DAY_OF_WEEK.eq(dayOfWeek.toByte()))
                                .and(USER.ENABLE.eq(1))
                                .and(USER.ID.eq(userId))
                                .and(COURSE_TIME.START_TIME.eq(nextClass.toByte()))
                        )
                        .groupBy(USER.ID).fetchOne()
                }
                val msg = buildString {
                    append("您好!接下来是第${nextClass}节课," +
                            "上课时间${AppConfig.getInstance().classTime[nextClass -1]}\n")
                    append(
                        "${course.getValue(COURSE.NAME)}，在${course.getValue(CLASSROOM.LOCATION)}，${
                            course.getValue(
                                COURSE.SCORE
                            )
                        }个学分"
                    )
                }

                BotsManager.sendMsg(bot, account, PlainText(msg))
                logger.info("向用户${userId}发送上课提醒成功")
            } catch (e: Exception) {
                logger.error(e)
            }

        }
    }
}