package io.github.mzdluo123.timetablebot.bots.listeners

import io.github.mzdluo123.timetablebot.command.CommandProcessor
import io.github.mzdluo123.timetablebot.controller.RootController
import io.github.mzdluo123.timetablebot.utils.withCatching
import net.mamoe.mirai.event.EventHandler
import net.mamoe.mirai.message.FriendMessageEvent


class BotMsgListener : BaseListeners() {

    private val processor = CommandProcessor<FriendMessageEvent>(listOf(RootController::class))

    @EventHandler
    suspend fun FriendMessageEvent.onEvent() {
       withCatching {
           processor.process(this)
           null
       }
    }


}
