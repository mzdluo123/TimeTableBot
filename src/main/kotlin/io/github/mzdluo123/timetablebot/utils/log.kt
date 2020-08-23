package io.github.mzdluo123.timetablebot.utils

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.runBlocking
import net.mamoe.mirai.event.Event
import net.mamoe.mirai.event.events.BotEvent
import net.mamoe.mirai.message.MessageEvent
import net.mamoe.mirai.message.data.Message
import net.mamoe.mirai.message.data.MessageChain
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.apache.logging.log4j.util.StackLocatorUtil


fun logger(): Logger {
    return LogManager.getLogger(StackLocatorUtil.getStackTraceElement(2).className)
}

private val thisLogger = logger()
val globalExceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
    thisLogger.error(throwable)
}

inline fun <reified T : MessageEvent> businessExceptionHandler(event: T, throwable: Throwable?) {
    runBlocking {
        event.reply("发生错误: ${throwable?.message}，请重试")
    }
}


suspend inline fun <reified T : MessageEvent> T.withCatching(
    exceptionHandler: (T, Throwable?) -> Unit = ::businessExceptionHandler,
    body: (T) -> Message?
) {
    val result = kotlin.runCatching { body(this) }
    if (result.isSuccess) {
        result.getOrNull()?.let { this.reply(it) }
    } else {
        exceptionHandler(this, result.exceptionOrNull())
    }
}