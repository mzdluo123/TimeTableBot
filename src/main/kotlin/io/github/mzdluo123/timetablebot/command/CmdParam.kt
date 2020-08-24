package io.github.mzdluo123.timetablebot.command

import io.github.mzdluo123.timetablebot.bots.BotsManager
import kotlinx.coroutines.*
import net.mamoe.mirai.message.MessageEvent
import net.mamoe.mirai.message.data.PlainText
import kotlin.coroutines.CoroutineContext

class CmdParam<S : MessageEvent>(val description: String = "暂无描述") :
    CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + SupervisorJob(BotsManager.jobs) + waitingJob

    val waitingJob = Job()
    suspend inline fun <reified T> BaseCmdController.getValue(source: S, msg: String?): T {
        var cmd:String? = msg
        if (cmd == null) {
            val requestSession = CompletableDeferred<String>(waitingJob)
            this.cmdProcessor.addUnCompleteSession(source.sender.id,requestSession)
            timeout(this.cmdProcessor, source.sender.id)
            source.reply(
                PlainText(
                    """缺少参数： $description
类型: ${T::class}
请在60秒内直接发送即可
            """.trimMargin()
                )
            )
            cmd = requestSession.await()
            waitingJob.cancel()
            cmdProcessor.delUnCompleteSession(source.sender.id)
        }
        try {
            return when (T::class) {
                Int::class -> {
                    cmd.toInt() as T
                }
                Long::class -> {
                    cmd.toLong() as T
                }
                String::class -> {
                    cmd as T
                }
                Boolean::class -> {
                    val trim = msg!!.trim().toLowerCase()
                    if (trim == "好" || trim == "是" || trim == "true" || trim == "yes" || trim == "y" || trim == "1") {
                        true as T
                    } else {
                        false as T
                    }

                }
                else -> throw IllegalStateException("不支持的参数 ${T::class.java.name}")
            }

        } catch (e: Exception) {

            throw  IllegalStateException("无法转换 $msg 到参数 ${T::class}")
        }
    }

    fun timeout(cmdProcessor: CommandProcessor<*>, user:Long) {
        this.launch {
            delay(60 * 1000)
            waitingJob.cancel()
            cmdProcessor.delUnCompleteSession(user)
        }
    }
}