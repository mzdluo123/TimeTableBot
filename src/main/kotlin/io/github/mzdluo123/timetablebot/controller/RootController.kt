package io.github.mzdluo123.timetablebot.controller

import io.github.mzdluo123.timetablebot.command.BaseCmdController
import io.github.mzdluo123.timetablebot.command.CmdMapping
import io.github.mzdluo123.timetablebot.command.CmdParam
import io.github.mzdluo123.timetablebot.command.ParamDescription
import net.mamoe.mirai.message.FriendMessageEvent

class RootController : BaseCmdController("t",null,"这是描述") {

    @CmdMapping("help", des = "help")
    suspend fun help(
        e: FriendMessageEvent,
        @ParamDescription("这是描述") page: CmdParam<FriendMessageEvent>,
    ) {
        val str: String = page.getValue(e, cmdProcessor)
        e.reply(str)
    }
}