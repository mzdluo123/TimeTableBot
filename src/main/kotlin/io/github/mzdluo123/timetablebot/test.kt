package io.github.mzdluo123.timetablebot
import io.github.mzdluo123.timetablebot.config.AppConfig
import io.github.mzdluo123.timetablebot.gen.timetable.tables.*
import io.github.mzdluo123.timetablebot.utils.dbCtx
import java.io.File

fun main() {
    AppConfig.loadConfig(File("config.yml"))

    val data = dbCtx {
        return@dbCtx it.select(
                Course.COURSE.NAME,
                Course.COURSE.TEACHER,
                Classroom.CLASSROOM.LOCATION,
                Course.COURSE.SCORE,
                Course.COURSE.WEEK_PERIOD,
                Course.COURSE.PERIOD,
                CourseTime.COURSE_TIME.START_TIME
        )
                .from(
                        UserCourse.USER_COURSE.innerJoin(User.USER).on(UserCourse.USER_COURSE.USER.eq(User.USER.ID))
                                .innerJoin(Course.COURSE).on(UserCourse.USER_COURSE.COURSE.eq(Course.COURSE.ID))
                                .innerJoin(CourseTime.COURSE_TIME).on(CourseTime.COURSE_TIME.COURSE.eq(Course.COURSE.ID))
                                .innerJoin(Classroom.CLASSROOM).on(CourseTime.COURSE_TIME.CLASS_ROOM.eq(Classroom.CLASSROOM.ID))

                )
                .where(
                        CourseTime.COURSE_TIME.WEEK.eq(1)
                                .and(User.USER.ID.eq(5))
                                .and(CourseTime.COURSE_TIME.DAY_OF_WEEK.eq(1))
                )
                .orderBy(
                        CourseTime.COURSE_TIME.START_TIME
                )
                .fetch()
    }
    println(data)
}