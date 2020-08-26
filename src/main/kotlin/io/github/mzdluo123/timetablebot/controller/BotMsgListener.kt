package io.github.mzdluo123.timetablebot.controller

import io.github.mzdluo123.timetablebot.route.CommandRoute
import io.github.mzdluo123.timetablebot.route.cmdArg
import io.github.mzdluo123.timetablebot.route.route
import net.mamoe.mirai.event.EventHandler
import net.mamoe.mirai.message.FriendMessageEvent
import net.mamoe.mirai.message.data.PlainText


class BotMsgListener : BaseListeners() {

    @EventHandler
    suspend fun FriendMessageEvent.onEvent() {
        route(prefix = "", delimiter = " ") {
            exception { throwable ->
                PlainText(throwable.toString())
            }
            case("1","一号命令") {
                val arg1: String by cmdArg(0, "这是一个字符串", it)
                val arg2: Int by cmdArg(1, "这是一个数字", it)
                val arg3: Boolean by cmdArg(2, "这是一个Boolean", it)
                reply(PlainText("$arg1 $arg2 $arg3"))
            }
            case("2","二号命令") {
                reply("啦啦啦啦")
            }
            case("3","异常测试"){
                throw IllegalAccessError("2333")
            }
            nextRoute("lalal","", ::next)
            default {
                reply(PlainText(generateHelp()))

            }

        }

    }

    suspend fun next(route: CommandRoute<FriendMessageEvent>) {
        route.case("dsd") {

            reply("dsd")
        }
        route.default {

            reply(PlainText(route.generateHelp()))
        }
    }


}

