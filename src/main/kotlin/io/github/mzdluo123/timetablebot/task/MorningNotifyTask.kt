package io.github.mzdluo123.timetablebot.task

import io.github.mzdluo123.timetablebot.appJob
import io.github.mzdluo123.timetablebot.data.getHitokoto
import io.github.mzdluo123.timetablebot.gen.timetable.tables.daos.UserDao
import io.github.mzdluo123.timetablebot.utils.createDao
import io.github.mzdluo123.timetablebot.utils.logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.quartz.Job
import org.quartz.JobExecutionContext
import kotlin.coroutines.CoroutineContext

class MorningNotifyTask() :Job,CoroutineScope{

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + SupervisorJob(appJob)
    private val logger = logger()
    private val userDao = createDao(UserDao::class)
    override fun execute(context: JobExecutionContext?) {
        launch { run() }
    }

    private suspend fun run(){
        val users = userDao.fetchByEnable(1)
        val hitokoto = getHitokoto()
        users.forEach {
            // todo  发送早安


        }

    }

}