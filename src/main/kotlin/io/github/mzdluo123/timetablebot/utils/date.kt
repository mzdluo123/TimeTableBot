package io.github.mzdluo123.timetablebot.utils

import io.github.mzdluo123.timetablebot.config.AppConfig
import java.io.File
import java.time.Instant
import java.time.LocalTime
import java.time.ZoneId
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

// 获取下一节课的时间下标
fun nextClassIndex(): Int {
    val now = LocalTime.now()
    Dependencies.classTimeTable.forEachIndexed { index, time ->
        if (now.isBefore(time)) {
            return if (index - 1 >= 0 && now.isAfter(Dependencies.classTimeTable[index - 1])) {
                index
            }else{
                0
            }
        }
    }
    return Int.MAX_VALUE
}

fun main() {
    AppConfig.loadConfig(File("config.yml"))
    println(nextClassIndex())
}