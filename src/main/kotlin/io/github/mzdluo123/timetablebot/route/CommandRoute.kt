package io.github.mzdluo123.timetablebot.route

import io.github.mzdluo123.timetablebot.appJob
import io.github.mzdluo123.timetablebot.utils.logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import net.mamoe.mirai.message.MessageEvent
import net.mamoe.mirai.message.data.Message
import kotlin.coroutines.CoroutineContext

class CommandRoute<T : MessageEvent>(val args: List<String>?, val event: T) : CoroutineScope {
    private var called = false
    private var errHandler: (suspend (Throwable) -> Message?)? = null

    private val job = Job()
    private val logger = logger()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default + SupervisorJob(appJob) + job

    suspend fun case(case: String, receiver: suspend T.(List<String>?) -> Unit) {
        if (case == args?.get(0)) {
            kotlin.runCatching { event.receiver(args.subList(1, args.size)) }.also {
                handleException(it.exceptionOrNull())
            }
        }
    }

    suspend fun default(receiver: suspend T.(List<String>?) -> Unit) {
        if (!called) {
            kotlin.runCatching { event.receiver(args) }.also {
                handleException(it.exceptionOrNull())
            }
        }
    }

    suspend fun nextRoute(case: String, next: suspend (CommandRoute<T>) -> Unit) {
        if (case == args?.get(0)) {
            kotlin.runCatching { next(CommandRoute(args.subList(1, args.size), event)) }.also {
                handleException(it.exceptionOrNull())
            }
        }
    }

    suspend fun exception(receiver: suspend (Throwable) -> Message?) {
        errHandler = receiver
    }

    private suspend fun handleException(throwable: Throwable?) {
        logger.error(throwable)
        val repMsg = errHandler?.let { it1 -> it1(throwable ?: return) }
        event.reply(repMsg ?: return)
    }
}

inline fun <reified T : MessageEvent> T.route(
    prefix: String = "",
    delimiter: String = " ",
    receiver: CommandRoute<T>.() -> Unit
): Boolean {
    val msg = this.message.contentToString()
    if (!msg.startsWith(prefix)) {
        return false
    }
    val args = msg.split(delimiter)
    receiver(CommandRoute(args, this))
    return true
}

