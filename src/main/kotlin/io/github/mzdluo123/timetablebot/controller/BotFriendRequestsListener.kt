package io.github.mzdluo123.timetablebot.controller

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import net.mamoe.mirai.event.EventHandler
import net.mamoe.mirai.event.events.NewFriendRequestEvent

class BotFriendRequestsListener : BaseListeners() {

    @EventHandler
    suspend fun NewFriendRequestEvent.onEvent() {
        this.accept()
        launch {
            delay(2000L)
            bot.getFriend(fromId).sendMessage(
                  """  
                      欢迎使用课表小助手
                      请发送 开始 来快速开始使用
                      请发送任意消息来获得帮助
                  """.trimIndent()
            )
        }
        logger.info("accepted friend request $this")
    }

}