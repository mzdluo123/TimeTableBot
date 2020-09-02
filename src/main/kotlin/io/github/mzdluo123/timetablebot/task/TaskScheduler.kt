package io.github.mzdluo123.timetablebot.task

import io.github.mzdluo123.timetablebot.config.AppConfig
import io.github.mzdluo123.timetablebot.utils.logger
import io.github.mzdluo123.timetablebot.utils.parseClassTime
import org.quartz.CronScheduleBuilder
import org.quartz.JobBuilder
import org.quartz.Trigger
import org.quartz.TriggerBuilder
import org.quartz.impl.StdSchedulerFactory
import java.time.format.DateTimeFormatter

object TaskScheduler {
    private val scheduler = StdSchedulerFactory()
    private val logger = logger()

    fun init() {
        scheduleNotifyTask()
    }

    fun scheduleNotifyTask() {
        val scheduler = scheduler.scheduler
        val job = JobBuilder.newJob(NotifyTask::class.java).withDescription("报课任务").build()
        val triggers = mutableSetOf<Trigger>()
        for (classTime in AppConfig.getInstance().classTime) {
            val time = parseClassTime(classTime)
            val before = time.minusMinutes(35)
            triggers.add(
                TriggerBuilder.newTrigger()
                    .withDescription("报课调度器:${time.format(DateTimeFormatter.ISO_LOCAL_TIME)}")
                    .startNow()
                    .withSchedule(CronScheduleBuilder.cronSchedule("0 ${before.minute} ${before.hour} * * ?"))
                    .build()
            )
        }
        scheduler.scheduleJob(job, triggers, true)
        logger.info("报课任务安排成功，共安排了${triggers.size}个任务")
    }

}