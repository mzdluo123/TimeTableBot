package io.github.mzdluo123.timetablebot.route

import io.github.mzdluo123.timetablebot.appJob
import io.github.mzdluo123.timetablebot.utils.logger
import kotlinx.coroutines.*
import net.mamoe.mirai.message.MessageEvent
import net.mamoe.mirai.message.data.Message
import kotlin.coroutines.CoroutineContext

val unCompleteValue = hashMapOf<Long, CompletableDeferred<String>>()

class CommandRoute<T : MessageEvent>(private val args: List<String>?, private val event: T) : CoroutineScope {
    private var called = false
    private var errHandler: (suspend (Throwable) -> Message?)? = null

    private var commandMap = hashMapOf<String, String>()

    private val job = Job()
    private val logger = logger()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default + SupervisorJob(appJob) + job

    suspend fun case(case: String, desc: String = "暂无描述", receiver: suspend T.(List<String>?) -> Unit) {
        commandMap[case] = desc
        if (args?.size == 0){
            return
        }
        if (!called && case == args?.get(0)) {
            called = true
            kotlin.runCatching { event.receiver(args.subList(1, args.size)) }.also {
                handleException(it.exceptionOrNull())
            }
        }
    }
    // 必须放在最后一个
    suspend fun default(receiver: suspend T.(List<String>?) -> Unit) {
        if (!called) {
            kotlin.runCatching { event.receiver(args) }.also {
                handleException(it.exceptionOrNull())
            }
        }
    }

    suspend fun nextRoute(case: String, desc: String = "暂无描述", next: suspend (CommandRoute<T>) -> Unit) {
        commandMap[case] = desc
        if (args?.size == 0){
            return
        }
        if (!called && case == args?.get(0)) {
            called = true
            kotlin.runCatching { next(CommandRoute(args.subList(1, args.size), event)) }.also {
                handleException(it.exceptionOrNull())
            }
        }
    }

    // 必须放在第一个
    fun exception(receiver: suspend (Throwable) -> Message?) {
        errHandler = receiver
    }

    private suspend fun handleException(throwable: Throwable?) {
        logger.error(throwable?:return)
        val repMsg = errHandler?.let { it1 -> it1(throwable) }
        event.reply(repMsg ?: return)
    }

    fun generateHelp():String = buildString {
        append("命令帮助\n")
        append(commandMap.asSequence().joinToString(separator = "\n") { "${it.key} : ${it.value}" })
    }

}

inline fun <reified T : MessageEvent> T.route(
    prefix: String = "",
    delimiter: String = " ",
    receiver: CommandRoute<T>.() -> Unit
): Boolean {
    val msg = this.message.contentToString()

    if (unCompleteValue.containsKey(this.sender.id)) {
        unCompleteValue[this.sender.id]?.complete(msg)
        return true
    }

    if (!msg.startsWith(prefix)) {
        return false
    }
    val args = msg.split(delimiter)
    receiver(CommandRoute(args, this))
    return true
}