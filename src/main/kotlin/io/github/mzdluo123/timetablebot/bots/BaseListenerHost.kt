package io.github.mzdluo123.timetablebot.bots

import kotlinx.coroutines.*
import net.mamoe.mirai.event.ListenerHost
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

public abstract class BaseListenerHost
@JvmOverloads constructor(coroutineContext: CoroutineContext = EmptyCoroutineContext) : ListenerHost, CoroutineScope {

    public final override val coroutineContext: CoroutineContext =
        CoroutineExceptionHandler(::handleException) + coroutineContext + SupervisorJob(coroutineContext[Job])

    /**
     * 处理事件处理中未捕获的异常. 在构造器中的 [coroutineContext] 未提供 [CoroutineExceptionHandler] 情况下必须继承此函数.
     */
    public open fun handleException(context: CoroutineContext, exception: Throwable) {
        throw IllegalStateException(
            """
            未找到异常处理器. 请继承 SimpleListenerHost 中的 handleException 方法, 或在构造 SimpleListenerHost 时提供 CoroutineExceptionHandler
            ------------
            Cannot find exception handler from coroutineContext. 
            Please extend SimpleListenerHost.handleException or provide a CoroutineExceptionHandler to the constructor of SimpleListenerHost
        """.trimIndent(), exception
        )
    }

    /**
     * 停止所有事件监听
     */
    public fun cancelAll() {
        this.cancel()
    }
}
