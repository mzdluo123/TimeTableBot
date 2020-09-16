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
                    "您好！欢迎使用课表小助手，快速开始：\n" +
                            "1.发送 \"init 您的学号\"以创建新账户,例如：init 123456789(init和学号之间有一个空格，以下相同)\n" +
                            "2.创建新账户以后发送 \"sync 您的个人门户密码\"来拉取您的课表,例如：sync password\n" +
                            "3.等待课表拉取完成，之后即可开始使用,您可以发送任意消息来查看指令帮助\n"+
                            "4.您可以回复\"td\"退订本bot的服务"
            )
        }
        logger.info("accepted friend request $this")
    }

}