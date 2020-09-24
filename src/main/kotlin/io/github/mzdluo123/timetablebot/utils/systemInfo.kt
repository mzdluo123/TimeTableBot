package io.github.mzdluo123.timetablebot.utils

import com.sun.management.OperatingSystemMXBean
import java.lang.management.ManagementFactory


private val osmxb: OperatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean() as OperatingSystemMXBean
private val memoryMXBean = ManagementFactory.getMemoryMXBean()


fun getSystemInfo(): String {
    val usage = memoryMXBean.heapMemoryUsage
    return """
    INT HEAP:${usage.init / 1024 / 1024}Mb
    MAX HEAP:${usage.max / 1024 / 1024}Mb
    USED HEAP:${usage.used / 1024 / 1024}Mb
    可用内存:${osmxb.freePhysicalMemorySize / 1024 / 1024}MB
    已用内存:${(osmxb.totalPhysicalMemorySize - osmxb.freePhysicalMemorySize) / 1024 / 1024}MB
    系统CPU:${String.format("%.2f", osmxb.systemCpuLoad * 100)}%
    进程CPU:${String.format("%.2f", osmxb.processCpuLoad * 100)}%
""".trimIndent()

}

fun main() {
    println(getSystemInfo())
}