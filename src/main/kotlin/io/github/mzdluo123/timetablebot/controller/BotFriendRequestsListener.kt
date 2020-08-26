package io.github.mzdluo123.timetablebot.controller

import net.mamoe.mirai.event.EventHandler
import net.mamoe.mirai.event.events.NewFriendRequestEvent

class BotFriendRequestsListener : BaseListeners() {

    @EventHandler
    suspend fun NewFriendRequestEvent.onEvent() {
        this.accept()
        logger.info("accepted friend request $this")
    }

}