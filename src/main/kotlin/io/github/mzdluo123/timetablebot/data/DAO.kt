package io.github.mzdluo123.timetablebot.data

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.jodatime.datetime

object User : IntIdTable() {
    val account = long("account").uniqueIndex()
    val studentId = integer("student_id").uniqueIndex()  // 学号
    val lastActiveDate = datetime("last_active").nullable()
    val joinTime = datetime("join_time").nullable()

}


object Course : IntIdTable() {
    val name = varchar("name", 10)
    val teacher = varchar("thacher", 10)
    val courseId = varchar("course_id", 32)

    val points = float("points") // 绩点
    val score = float("score")  //学分
}

object ClassRoom : IntIdTable() { // 上课地点，同一个地点不同时间有不同的课
    val location = varchar("location", 10).uniqueIndex().nullable()
}


object CourseTimeTable : IntIdTable() {
    val course = reference("course", Course).index()
    val classRoom = reference("class_room", ClassRoom).index()  // 同一个课也会有不同的教室

    val dayOfWeek = char("day_of_week").index() // 星期几
    val week = char("week").index()  // 记录所有周数

    val start = short("start_time").index()  // 第几节
    val length = short("length").index()
}

object UserCourse : IntIdTable() {  // 一个用户有多个课程 一个课程有多个用户
    val user = reference("user", User)
    val course = reference("course", Course)
}