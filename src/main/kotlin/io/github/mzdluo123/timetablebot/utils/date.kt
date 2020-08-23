package io.github.mzdluo123.timetablebot.utils

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*


private val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
    .withLocale(Locale.CHINA)
    .withZone(ZoneId.systemDefault())

fun timeToStr(time: Long): String {
    return formatter.format(Instant.ofEpochMilli(time))
}