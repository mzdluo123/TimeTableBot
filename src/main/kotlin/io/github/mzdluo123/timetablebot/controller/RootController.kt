package io.github.mzdluo123.timetablebot.controller

import io.github.mzdluo123.timetablebot.command.BaseCmdController
import io.github.mzdluo123.timetablebot.command.CmdMapping
import io.github.mzdluo123.timetablebot.command.CmdParam
import io.github.mzdluo123.timetablebot.command.CommandProcessor
import kotlinx.coroutines.launch
import net.mamoe.mirai.message.FriendMessageEvent

class RootController : BaseCmdController("help") {

    @CmdMapping("help", des = "help")
    fun help(
        e: FriendMessageEvent,
        page: CmdParam<FriendMessageEvent>,
        cmdProcessor: CommandProcessor<FriendMessageEvent>
    ) {
        launch {
            val str: String = page.getValue(e, cmdProcessor)
            e.reply(str)
        }

    }
}