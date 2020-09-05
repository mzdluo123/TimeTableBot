package io.github.mzdluo123.timetablebot.route

import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeoutOrNull
import net.mamoe.mirai.message.MessageEvent
import net.mamoe.mirai.message.data.PlainText
import java.util.concurrent.TimeoutException
import kotlin.reflect.KProperty

class CommandArg<T : MessageEvent>(val index: Int = 0, val desc: String, val args: List<String>?, val event: T) {
    var data: String? = null
    val waitValue = CompletableDeferred<String>()

    inline operator fun <reified T> getValue(n: Nothing?, property: KProperty<*>): T {
        if (args != null && data == null && index < args.size) {
            data = args[index]
        }
        if (data == null) {
            unCompleteValue[event.sender.id] = waitValue
            runBlocking {
                event.reply(
                    PlainText(
                        """缺少参数:$desc
                    |类型:${property.returnType}
                    |请在60秒内输入即可
                """.trimMargin()
                    )
                )
                data = withTimeoutOrNull(60 * 1000) {
                    waitValue.await()
                }
                unCompleteValue.remove(event.sender.id)
                if (data == null) {
                    throw TimeoutException("获取参数超时")
                }
            }
        }

        return when (T::class) {
            Int::class -> {
                data!!.toInt() as T
            }
            Long::class -> {
                data!!.toLong() as T
            }
            String::class -> {
                data as T
            }
            Boolean::class -> {
                val trim = data!!.trim().toLowerCase()
                if (trim == "好" || trim == "是" || trim == "true" || trim == "yes" || trim == "y" || trim == "1") {
                    true as T
                } else {
                    false as T
                }

            }
            else -> throw IllegalStateException("不支持的参数 ${T::class.java.name}")
        }
    }
}

fun <T : MessageEvent> T.cmdArg(index: Int = 0, desc: String, args: List<String>?) =
    CommandArg<T>(index, desc, args, this)


