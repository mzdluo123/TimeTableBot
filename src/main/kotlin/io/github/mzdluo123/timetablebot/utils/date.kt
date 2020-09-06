package io.github.mzdluo123.timetablebot.utils

import io.github.mzdluo123.timetablebot.config.AppConfig
import io.github.mzdluo123.timetablebot.controller.searchNextClass
import io.github.mzdluo123.timetablebot.controller.searchTomorrowNextClass
import io.github.mzdluo123.timetablebot.gen.timetable.tables.*
import java.io.File
import java.time.*
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale


private val timeFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
    .withLocale(Locale.CHINA)
    .withZone(ZoneId.systemDefault())


private val timeParser = DateTimeFormatter.ofPattern("HH:mm")
    .withLocale(Locale.CHINA)
    .withZone(ZoneId.systemDefault())

fun timeToStr(time: Long): String {
    return timeFormatter.format(Instant.ofEpochMilli(time))
}

fun parseClassTime(timeStr: String): LocalTime {
    return LocalTime.parse(timeStr, timeParser)
}

fun nextClass(user: io.github.mzdluo123.timetablebot.gen.timetable.tables.pojos.User):String {
    var week = dayOfWeek()
    val now:LocalTime= LocalTime.now()
    val course= searchNextClass(week,user,now)
    return if (course!=null){
         buildString {
            append("您好!接下来是第${nextClassIndex().toByte()}节课，上课时间${AppConfig.getInstance().classTime[nextClassIndex()]}\n")
            append(
                    "${course.getValue(Course.COURSE.NAME)}，在${course.getValue(Classroom.CLASSROOM.LOCATION)}，${
                    course.getValue(
                            Course.COURSE.SCORE
                    )
                    }个学分"
            )
        }
    }else{
        //查询第二天的课表
        buildString {
            week = if (week<7) week+1 else 1;
            val course=                searchTomorrowNextClass(week,user,now)
            if (course != null) {
                append("您今日已无课，接下来是明天的第${course.component6()}节课," +
                        "上课时间${AppConfig.getInstance().classTime[course.component6().toInt()]}\n")
                append(
                        "${course.getValue(Course.COURSE.NAME)}，在${course.getValue(Classroom.CLASSROOM.LOCATION)}，${
                        course.getValue(
                                Course.COURSE.SCORE
                        )
                        }个学分"
                )
            }else{
                append("您暂时没有课了哦(*/ω＼*)")
            }
        }
    }
}
// 获取下一节课的时间下标
fun nextClassIndex(): Int {
    val now = LocalTime.now()
    Dependencies.classTimeTable.forEachIndexed { index, time ->
        if (now.isBefore(time)) {
            return if (index - 1 >= 0 && now.isAfter(Dependencies.classTimeTable[index - 1])) {
                index + 1
            } else {
                1
            }
        }
    }
    return Int.MAX_VALUE
}
fun getNextIndex(){
    val now = LocalTime.now()
    AppConfig.getInstance().classTime.forEachIndexed{ index: Int, time: String? ->

    }
}
fun parseWeek(weekStr: String): List<Int> {
    val res = mutableListOf<Int>()
    val parts = weekStr.split(",")
    for (part in parts) {
        val numbers = "[0-9]+".toRegex().findAll(part).toList()
        if (numbers.size == 2){
            if ("单" in part) {
                for (i in numbers[0].value.toInt()..numbers[1].value.toInt() step 2) {
                    res.add(i)
                }
            } else {
                for (i in numbers[0].value.toInt()..numbers[1].value.toInt()) {
                    res.add(i)
                }
            }

        }
        if (numbers.size == 1){
            res.add(numbers[0].value.toInt())
        }
    }
    return res
}


/**
 * 获取现在是第几周
 * */
fun week(): Int {
    val begin = LocalDate.parse(AppConfig.getInstance().termBegin)
    //val today = LocalDate.of(2020,9,13)
    val today = LocalDate.now()
    val p = Period.between(begin,today)
    return p.days /7 +1

}

/**
 * 获取现在是星期几
 * */
fun dayOfWeek(): Int {
    return LocalDate.now().dayOfWeek.value
}

fun main() {
    AppConfig.loadConfig(File("config.yml"))
//    println(parseWeek("9-10周,13-18周"))
//    println(parseWeek("1-9周(单),13周"))
    println(week())
    println(dayOfWeek())
}