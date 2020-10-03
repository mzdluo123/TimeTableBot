package io.github.mzdluo123.timetablebot.utils

import io.github.mzdluo123.timetablebot.config.AppConfig
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

/**
 * 获取下一节课是第几节
 * 如果需要查询上课时间需要-1
 * */
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

fun parseWeek(weekStr: String): List<Int> {
    val res = mutableListOf<Int>()
    val parts = weekStr.split(",")
    for (part in parts) {
        val numbers = "[0-9]+".toRegex().findAll(part).toList()
        if (numbers.size == 2) {
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
        if (numbers.size == 1) {
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
    val today = LocalDate.now()
    val ans =  (today.toEpochDay() - begin.toEpochDay()) / 7 + 1
    return ans.toInt()

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