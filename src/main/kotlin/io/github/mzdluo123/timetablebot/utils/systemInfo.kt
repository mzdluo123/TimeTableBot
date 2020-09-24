package io.github.mzdluo123.timetablebot.utils

import com.sun.management.OperatingSystemMXBean
import java.lang.management.ManagementFactory


private val osmxb: OperatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean() as OperatingSystemMXBean

fun getSystemInfo(): String = """
    可用内存:${osmxb.freePhysicalMemorySize / 1024 / 1024}MB
    已用内存:${(osmxb.totalPhysicalMemorySize - osmxb.freePhysicalMemorySize) / 1024 / 1024}MB
    系统CPU:${String.format("%.2f", osmxb.systemCpuLoad * 100)}%
    进程CPU:${String.format("%.2f", osmxb.processCpuLoad * 100)}%
""".trimIndent()
