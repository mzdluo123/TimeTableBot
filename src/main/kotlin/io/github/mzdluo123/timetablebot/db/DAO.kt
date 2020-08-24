package io.github.mzdluo123.timetablebot.db

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.jodatime.datetime

object User : IntIdTable() {
    val account = long("account").uniqueIndex()
    val studentId = integer("student_id").uniqueIndex()
    val `class` = reference("class", Class)

    val lastActiveDate = datetime("last_active").nullable()
    val joinTime = datetime("join_time").nullable()
}

object Class : IntIdTable() {
    val classId = integer("class_id").uniqueIndex()
}

object Course : IntIdTable() {
    val name = varchar("name", 10)
    val teacher = varchar("thacher",10)
    val courseId = varchar("course_id",10)
    val classRoom = varchar("class_room",10)
    val startWeek = integer("start_week")
    val endWeek = integer("end_week")

    val startTime = datetime("start_time").index()
    val endTime = datetime("end_time").index()
}

object ClassCourse : IntIdTable() {
    val `class` = reference("class", Class).index()
    val course = reference("course", Course).index()
}