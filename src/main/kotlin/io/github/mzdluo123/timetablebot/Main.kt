package io.github.mzdluo123.timetablebot

import io.github.mzdluo123.timetablebot.bots.BotsManager
import io.github.mzdluo123.timetablebot.config.AppConfig
import io.github.mzdluo123.timetablebot.controller.BaseListeners
import io.github.mzdluo123.timetablebot.utils.dbCtx
import io.github.mzdluo123.timetablebot.utils.logger
import io.github.mzdluo123.timetablebot.utils.timeToStr
import io.io.github.mzdluo123.timetablebot.BuildConfig
import kotlinx.coroutines.Job
import kotlinx.coroutines.joinAll
import net.mamoe.mirai.event.registerEvents
import java.io.File

private val mainLogger = logger()

internal val appJob = Job()

suspend fun main(args: Array<String>) {
    println(
        """
 _________  ___  _____ ______   _______  _________  ________  ________  ___       _______   ________  ________  _________   
|\___   ___\\  \|\   _ \  _   \|\  ___ \|\___   ___\\   __  \|\   __  \|\  \     |\  ___ \ |\   __  \|\   __  \|\___   ___\ 
\|___ \  \_\ \  \ \  \\\__\ \  \ \   __/\|___ \  \_\ \  \|\  \ \  \|\ /\ \  \    \ \   __/|\ \  \|\ /\ \  \|\  \|___ \  \_| 
     \ \  \ \ \  \ \  \\|__| \  \ \  \_|/__  \ \  \ \ \   __  \ \   __  \ \  \    \ \  \_|/_\ \   __  \ \  \\\  \   \ \  \  
      \ \  \ \ \  \ \  \    \ \  \ \  \_|\ \  \ \  \ \ \  \ \  \ \  \|\  \ \  \____\ \  \_|\ \ \  \|\  \ \  \\\  \   \ \  \ 
       \ \__\ \ \__\ \__\    \ \__\ \_______\  \ \__\ \ \__\ \__\ \_______\ \_______\ \_______\ \_______\ \_______\   \ \__\
        \|__|  \|__|\|__|     \|__|\|_______|   \|__|  \|__|\|__|\|_______|\|_______|\|_______|\|_______|\|_______|    \|__|
    """.trimIndent()
    )
    Runtime.getRuntime().addShutdownHook(Thread {
        mainLogger.info("shutting down...")
        appJob.cancel()
    })

    mainLogger.info("TimeTableBot ${BuildConfig.VERSION} build ${timeToStr(BuildConfig.BUILD_UNIXTIME)} loading.... ")

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
    mainLogger.info("config loaded")

    mainLogger.info("connecting database")

    dbCtx().use {
        it.select().limit(1).fetch()
    }

    mainLogger.info("database connected")
    BaseListeners.listeners.forEach {
        BotsManager.registerEvents(it)
    }
    mainLogger.info("event listener registered")
    BotsManager.loginBots(AppConfig.getInstance().botAccounts)
    joinAll(BotsManager.jobs)
}

