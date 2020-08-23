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
import io.github.mzdluo123.timetablebot.utils.globalExceptionHandler
import io.github.mzdluo123.timetablebot.utils.logger
import kotlinx.coroutines.*
import net.mamoe.mirai.Bot
import net.mamoe.mirai.utils.internal.logging.Log4jLogger
import org.apache.logging.log4j.LogManager
import java.io.File
import kotlin.coroutines.CoroutineContext

object BotsManager : CoroutineScope {
    val jobs = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default + SupervisorJob(appJob) + jobs

    private val logger = logger()

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
    }

}