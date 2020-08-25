package io.github.mzdluo123.timetablebot.route

import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeoutOrNull
import java.util.concurrent.TimeoutException
import kotlin.reflect.KProperty

class CommandArg(val index: Int = 0, val desc: String, val args: List<String>?) {
    lateinit var data: String
    val waitValue = CompletableDeferred<String>()

    inline operator fun <reified T> getValue(n:Nothing?, property: KProperty<*>): T {
        if (args != null && index < args.size) {
            data = args[index]
        } else {

            runBlocking {
                data =  withTimeoutOrNull(60*1000){
                    waitValue.await()
                } ?: throw TimeoutException("等待输入 $desc 时超时")
            }
        }

        return when (T::class) {
            Int::class -> {
                data.toInt() as T
            }
            Long::class -> {
                data.toLong() as T
            }
            String::class -> {
                data as T
            }
            Boolean::class -> {
                val trim = data.trim().toLowerCase()
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


