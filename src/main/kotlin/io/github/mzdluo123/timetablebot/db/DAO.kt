package io.github.mzdluo123.timetablebot.db

import com.mysql.cj.xdevapi.Column
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.jodatime.datetime
import org.jetbrains.exposed.sql.stringParam

object User : IntIdTable() {
    val account = long("account").uniqueIndex()
    val studentId = integer("student_id").uniqueIndex()  // 学号
    val `class` = reference("class", Class)
    val password= varchar("password",11)// 密码
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
    val dayOfWeek = short("day_of_week").index()

    val start = short("start_time").index()
    val length = short("length").index()

    val points = float("points") // 绩点
    val score = float("score")  //学分

}

object ClassCourse : IntIdTable() {
    val `class` = reference("class", Class).index()
    val course = reference("course", Course).index()
}