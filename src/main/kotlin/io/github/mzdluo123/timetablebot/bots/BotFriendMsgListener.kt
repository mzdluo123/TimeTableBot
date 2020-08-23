package io.github.mzdluo123.timetablebot.bots

import io.github.mzdluo123.timetablebot.utils.logger
import io.github.mzdluo123.timetablebot.utils.withCatching
import net.mamoe.mirai.event.EventHandler
import net.mamoe.mirai.event.SimpleListenerHost
import net.mamoe.mirai.message.FriendMessageEvent
import net.mamoe.mirai.message.data.PlainText
import net.mamoe.mirai.message.data.toMessage
import kotlin.coroutines.CoroutineContext

class BotFriendMsgListener : SimpleListenerHost() {
    private val logger = logger()

    @EventHandler
    suspend fun FriendMessageEvent.onEvent() {
        withCatching {

            PlainText("test")
        }
    }


    override fun handleException(context: CoroutineContext, exception: Throwable) {
        logger.error(exception)
    }

}
