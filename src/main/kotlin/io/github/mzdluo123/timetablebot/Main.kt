package io.github.mzdluo123.timetablebot

import io.github.mzdluo123.timetablebot.bots.BotsManager
import io.github.mzdluo123.timetablebot.config.AppConfig
import io.github.mzdluo123.timetablebot.controller.BaseListeners
import io.github.mzdluo123.timetablebot.task.TaskScheduler
import io.github.mzdluo123.timetablebot.utils.dbCtx
import io.github.mzdluo123.timetablebot.utils.logger
import io.github.mzdluo123.timetablebot.utils.timeToStr
import kotlinx.coroutines.Job
import kotlinx.coroutines.joinAll
import net.mamoe.mirai.event.registerEvents
import java.io.File


private val mainLogger = logger()

internal val appJob = Job()

suspend fun main(args: Array<String>) {
    println("""
 ________  __                        ________         __        __            _______               __     
/        |/  |                      /        |       /  |      /  |          /       \             /  |    
$$$$$$$$/ $$/  _____  ____    ______$$$$$$$$/______  $$ |____  $$ |  ______  $$$$$$$  |  ______   _$$ |_   
   $$ |   /  |/     \/    \  /      \  $$ | /      \ $$      \ $$ | /      \ $$ |__$$ | /      \ / $$   |  
   $$ |   $$ |$$$$$$ $$$$  |/$$$$$$  | $$ | $$$$$$  |$$$$$$$  |$$ |/$$$$$$  |$$    $$< /$$$$$$  |$$$$$$/   
   $$ |   $$ |$$ | $$ | $$ |$$    $$ | $$ | /    $$ |$$ |  $$ |$$ |$$    $$ |$$$$$$$  |$$ |  $$ |  $$ | __ 
   $$ |   $$ |$$ | $$ | $$ |$$$$$$$$/  $$ |/$$$$$$$ |$$ |__$$ |$$ |$$$$$$$$/ $$ |__$$ |$$ \__$$ |  $$ |/  |
   $$ |   $$ |$$ | $$ | $$ |$$       | $$ |$$    $$ |$$    $$/ $$ |$$       |$$    $$/ $$    $$/   $$  $$/ 
   $$/    $$/ $$/  $$/  $$/  $$$$$$$/  $$/  $$$$$$$/ $$$$$$$/  $$/  $$$$$$$/ $$$$$$$/   $$$$$$/     $$$$/
   https://github.com/mzdluo123/TimeTableBot
   built by HelloWorld and Umb
    """.trimIndent()
    )
    Runtime.getRuntime().addShutdownHook(Thread {
        mainLogger.info("shutting down...")
        appJob.cancel()
    })

    mainLogger.info("TimeTableBot ${BuildConfig.VERSION} build ${timeToStr(BuildConfig.BUILD_UNIXTIME)} loading.... ")

    mainLogger.info("now ${timeToStr(System.currentTimeMillis())}")

    mainLogger.info("loading config.")

    val configFile = if (args.isNotEmpty()) {
        File(args[0])
    } else {
        File("config.yml")
    }
    if (!configFile.exists()) {
        mainLogger.fatal("${configFile.name} not exists!")
        return
    }
    AppConfig.loadConfig(configFile)
    AppConfig.loadHelp()
    mainLogger.info("config loaded")

    mainLogger.info("connecting database")

    dbCtx {
        it.select().from().fetch()
    }

    mainLogger.info("database connected")
    BaseListeners.listeners.forEach {
        BotsManager.registerEvents(it)
    }

    mainLogger.info("event listener registered")
    TaskScheduler.init()
    BotsManager.loginBots(AppConfig.getInstance().botAccounts)
    joinAll(BotsManager.jobs)
}

