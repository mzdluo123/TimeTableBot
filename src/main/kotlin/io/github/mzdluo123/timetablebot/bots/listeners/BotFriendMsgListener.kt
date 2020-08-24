package io.github.mzdluo123.timetablebot.bots.listeners

import io.github.mzdluo123.timetablebot.command.CommandProcessor
import io.github.mzdluo123.timetablebot.controller.RootController
import net.mamoe.mirai.event.EventHandler
import net.mamoe.mirai.message.FriendMessageEvent


class BotFriendMsgListener : BaseListeners() {

    val processor = CommandProcessor<FriendMessageEvent>(listOf(RootController::class))

    @EventHandler
    suspend fun FriendMessageEvent.onEvent() {
        processor.process(this)
    }


}
