package io.github.mzdluo123.timetablebot.bots.listeners

import io.github.mzdluo123.timetablebot.utils.withCatching
import net.mamoe.mirai.event.EventHandler
import net.mamoe.mirai.message.FriendMessageEvent
import net.mamoe.mirai.message.data.PlainText

class BotFriendMsgListener : BaseListeners() {

    @EventHandler
    suspend fun FriendMessageEvent.onEvent() {
        logger.info("test")
        withCatching {
           PlainText("test")
        }
    }


}
