package io.github.mzdluo123.timetablebot.controller

import io.github.mzdluo123.timetablebot.command.*
import net.mamoe.mirai.message.FriendMessageEvent

class RootController : BaseCmdController("t",null,"这是描述") {

    @CmdMapping("help", des = "help")
    suspend fun help(
        e: FriendMessageEvent,
        @ParamDescription("这是描述") page: CmdParam<FriendMessageEvent>,
        cmdProcessor: CommandProcessor<FriendMessageEvent>
    ) {
        val str: String = page.getValue(e, cmdProcessor)
        e.reply(str)
    }
}