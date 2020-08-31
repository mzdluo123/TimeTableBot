package io.github.mzdluo123.timetablebot.task

import io.github.mzdluo123.timetablebot.gen.timetable.tables.CourseTime.COURSE_TIME
import io.github.mzdluo123.timetablebot.gen.timetable.tables.User.USER
import io.github.mzdluo123.timetablebot.gen.timetable.tables.UserCourse.USER_COURSE
import io.github.mzdluo123.timetablebot.utils.dayOfWeek
import io.github.mzdluo123.timetablebot.utils.dbCtx
import io.github.mzdluo123.timetablebot.utils.nextClassIndex
import io.github.mzdluo123.timetablebot.utils.week
import org.quartz.Job
import org.quartz.JobExecutionContext

class NotifyTask : Job {
    override fun execute(context: JobExecutionContext?) {
        val nextClass = nextClassIndex()
        val week = week()
        val dayOfWeek = dayOfWeek()
        if (nextClass == Int.MAX_VALUE) {
            return
        }
        // 获取有课的用户
        val users = dbCtx {
            return@dbCtx it.select(USER.ID, USER.ACCOUNT, USER.BOT)
                .from(USER.innerJoin(USER_COURSE).on(USER_COURSE.USER.eq(USER.ID)), COURSE_TIME)
                .where(COURSE_TIME.WEEK.eq(week.toByte())
                    .and(COURSE_TIME.DAY_OF_WEEK.eq(dayOfWeek.toByte()))
                    .and(USER.ENABLE.eq(1)))
                .orderBy(USER.ID).fetch()
        }

        if (nextClass == 0) {
            // todo 早晨提醒

        }
        // 获取全部有课的用户


    }
}