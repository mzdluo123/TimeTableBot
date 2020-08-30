package io.github.mzdluo123.timetablebot.task

import io.github.mzdluo123.timetablebot.utils.dayOfWeek
import io.github.mzdluo123.timetablebot.utils.nextClassIndex
import io.github.mzdluo123.timetablebot.utils.week
import org.quartz.Job
import org.quartz.JobExecutionContext

class NotifyTask :Job{
    override fun execute(context: JobExecutionContext?) {
        val nextClass = nextClassIndex()
        val week = week()
        val dayOfWeek = dayOfWeek()
        if (nextClass == Int.MAX_VALUE){
            return
        }
        if (nextClass == 0){
             // todo 早晨提醒

        }

        // 获取全部有课的用户


    }
}