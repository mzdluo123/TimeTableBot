package io.github.mzdluo123.timetablebot.task

import io.github.mzdluo123.timetablebot.appJob
import io.github.mzdluo123.timetablebot.bots.BotsManager
import io.github.mzdluo123.timetablebot.config.AppConfig
import io.github.mzdluo123.timetablebot.controller.searchTodayClass
import io.github.mzdluo123.timetablebot.data.getPoem

import io.github.mzdluo123.timetablebot.gen.timetable.tables.daos.UserDao
import io.github.mzdluo123.timetablebot.utils.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import net.mamoe.mirai.message.data.PlainText
import org.quartz.Job
import org.quartz.JobExecutionContext
import kotlin.coroutines.CoroutineContext

class MorningNotifyTask() : Job, CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + SupervisorJob(appJob)
    private val logger = logger()
    private val userDao = createDao(UserDao::class)
    override fun execute(context: JobExecutionContext?) {
        launch { run() }
    }

    private suspend fun run() {
        val users = userDao.fetchByEnable(1)
        val poem = try {
            getPoem().data.content
        }catch (e: Exception){
            logger.error(e)
            e.printStackTrace()
            ""
        }
        val week = week()
        val dayOfWeek = dayOfWeek()
        for (user in users){
            val courses = searchTodayClass(week, dayOfWeek, user) ?: continue
            val morning=if(courses.size == 0){
                 """早上好！今天是第${week}周的星期${dayOfWeek},您今天没有课哦，祝您有一个充实快乐的一天
                    |「${poem}」
                """.trimMargin()
            }else {
                """早上好！今天是第${week}周的星期${dayOfWeek}，您今天共有${courses.size}节课
                |以下是您今日的课程表
                |
                |「${poem}」
            """.trimMargin()
            }
            val classTable =
                    courses.joinToString(separator = "\n") {
                        """
${it.component1()}
${it.component3()} 
时间：${AppConfig.getInstance().classTime[it.component7().toInt() - 1]} (第${it.component7()}节)                             
--------------
""".trimIndent()
                    }
            BotsManager.sendMsg(user.id, PlainText(morning))
            BotsManager.sendMsg(user.id, PlainText(classTable))
            logger.info("发送早晨提醒信息到${user.id}成功")
        }

    }

}
