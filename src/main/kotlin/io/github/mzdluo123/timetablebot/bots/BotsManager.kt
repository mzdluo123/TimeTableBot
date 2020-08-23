@file:Suppress(
    "EXPERIMENTAL_API_USAGE",
    "DEPRECATION_ERROR",
    "OverridingDeprecatedMember",
    "INVISIBLE_REFERENCE",
    "INVISIBLE_MEMBER"
)

package io.github.mzdluo123.timetablebot.bots

import io.github.mzdluo123.timetablebot.config.Account
import io.github.mzdluo123.timetablebot.utils.globalExceptionHandler
import io.github.mzdluo123.timetablebot.utils.logger
import kotlinx.coroutines.*
import net.mamoe.mirai.Bot
import net.mamoe.mirai.alsoLogin
import net.mamoe.mirai.event.events.BotOfflineEvent
import net.mamoe.mirai.event.registerEvents
import net.mamoe.mirai.event.subscribeAlways
import net.mamoe.mirai.event.subscribeFriendMessages
import net.mamoe.mirai.utils.internal.logging.Log4jLogger
import org.apache.logging.log4j.LogManager
import java.io.File
import kotlin.coroutines.CoroutineContext

object BotsManager : CoroutineScope {
    val jobs = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default + jobs

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
            bots.forEach {
                logger.info("try login ${it.id}....")
                val botLogger = Log4jLogger(LogManager.getLogger("BOT-${it.id}"))
                val bot = Bot(it.id, it.pwd) {
                    fileBasedDeviceInfo(File(deviceInfoFolder, "${it.id}.json").absolutePath)
                    inheritCoroutineContext()
                    botLoggerSupplier = { _ ->
                        botLogger
                    }
                    networkLoggerSupplier = { _ ->
                        botLogger
                    }
                }
//                bot.subscribeAlways<BotOfflineEvent> {
//                    logger.fatal("bot ${bot.id} offline!")
//                    bot.cancel()
//                }
                bot.login()

            }

        }
    }

}