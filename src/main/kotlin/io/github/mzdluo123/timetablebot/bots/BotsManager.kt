@file:Suppress(
    "EXPERIMENTAL_API_USAGE",
    "DEPRECATION_ERROR",
    "OverridingDeprecatedMember",
    "INVISIBLE_REFERENCE",
    "INVISIBLE_MEMBER"
)

package io.github.mzdluo123.timetablebot.bots

import io.github.mzdluo123.timetablebot.appJob
import io.github.mzdluo123.timetablebot.config.Account
import io.github.mzdluo123.timetablebot.gen.timetable.tables.daos.UserDao
import io.github.mzdluo123.timetablebot.utils.createDao
import io.github.mzdluo123.timetablebot.utils.globalExceptionHandler
import io.github.mzdluo123.timetablebot.utils.logger
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import net.mamoe.mirai.Bot
import net.mamoe.mirai.message.data.Message
import net.mamoe.mirai.utils.internal.logging.Log4jLogger
import org.apache.logging.log4j.LogManager
import java.io.File
import kotlin.coroutines.CoroutineContext

object BotsManager : CoroutineScope {
    val jobs = Job()
    private val userDao = createDao(UserDao::class)

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO+ SupervisorJob(appJob) + jobs

    private val logger = logger()
    private val sendMsgChannel = Channel<SendMsgJob>()

    fun closeAllBot() {
        jobs.cancel()
    }

    fun loginBots(bots: List<Account>) {
        val deviceInfoFolder = File("devices")
        if (!deviceInfoFolder.exists()) {
            deviceInfoFolder.mkdir()
        }
        launch(globalExceptionHandler) {
            logger.info("try login ${bots.size} bot(s)....")
            bots.forEach {
                logger.info("try login ${it.id}....")

                val bot = Bot(it.id, it.pwd) {
                    fileBasedDeviceInfo(File(deviceInfoFolder, "${it.id}.json").absolutePath)
                    inheritCoroutineContext()
                    botLoggerSupplier = { _ ->
                        Log4jLogger(LogManager.getLogger("BOT-${it.id}"))
                    }
                    networkLoggerSupplier = { _ ->
                        Log4jLogger(LogManager.getLogger("NETWORK"))
                    }
                }
//                bot.subscribeAlways<BotOfflineEvent> {
//                    logger.fatal("bot ${bot.id} offline!")
//                    bot.cancel()
//                }
                bot.login()
            }
            logger.info("login finish")
        }
        launch { sendMsgJob() }
    }

    private suspend fun sendMsgJob(){
        for (msg in sendMsgChannel){

           kotlin.runCatching {  Bot.getInstance(msg.bot).getFriend(msg.target).sendMessage(msg.msg) }.also {
               if (it.isFailure){
                   logger.error("发送消息${msg}失败,原因:${it.exceptionOrNull()}")
               }
           }
            delay((1..10000).random().toLong())
        }
    }

    suspend fun sendMsg(user: Int, msg: Message) {
        val userPO = userDao.fetchOneById(user)
        sendMsgChannel.send(SendMsgJob(userPO.bot,userPO.account,msg))

    }

    suspend fun sendMsg(bot: Long, account: Long, msg: Message) {
        sendMsgChannel.send(SendMsgJob(bot,account,msg))
    }
}

data class SendMsgJob(val bot:Long,val target: Long,val msg: Message)