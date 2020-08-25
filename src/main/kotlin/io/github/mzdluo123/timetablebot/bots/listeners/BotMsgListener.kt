package io.github.mzdluo123.timetablebot.bots.listeners

import io.github.mzdluo123.timetablebot.route.CommandArg
import io.github.mzdluo123.timetablebot.route.CommandRoute
import io.github.mzdluo123.timetablebot.route.route
import net.mamoe.mirai.event.EventHandler
import net.mamoe.mirai.message.FriendMessageEvent
import net.mamoe.mirai.message.GroupMessageEvent
import net.mamoe.mirai.message.TempMessageEvent
import net.mamoe.mirai.message.data.PlainText


class BotMsgListener : BaseListeners() {

    @EventHandler
    suspend fun FriendMessageEvent.onEvent() {
        route(prefix = "!", delimiter = " ") {
            case("dsd") {

            }
            case("123") {
                reply("")
            }
            nextRoute("lalal", ::next)
            default {
                val arg1: String by CommandArg(1, "描述", it)
            }

            exception { throwable ->
                PlainText(throwable.toString())
            }
        }

    }

    suspend fun next(route: CommandRoute<FriendMessageEvent>) {
        route.case("dsd") {


        }
        route.default {


        }
    }

    @EventHandler
    suspend fun GroupMessageEvent.onEvent() {
        route {
            case("") {

            }

        }

    }

    @EventHandler
    suspend fun TempMessageEvent.onEvent() {
        route {
            case("") {


            }

        }

    }

}

