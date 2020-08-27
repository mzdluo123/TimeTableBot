package io.github.mzdluo123.timetablebot.controller

import io.github.mzdluo123.timetablebot.gen.timetable.tables.User
import io.github.mzdluo123.timetablebot.route.CommandRouter
import io.github.mzdluo123.timetablebot.route.cmdArg
import io.github.mzdluo123.timetablebot.route.route
import io.github.mzdluo123.timetablebot.utils.dbCtx
import net.mamoe.mirai.event.EventHandler
import net.mamoe.mirai.message.FriendMessageEvent
import net.mamoe.mirai.message.data.PlainText
import java.time.LocalDateTime


class BotMsgListener : BaseListeners() {

    @EventHandler
    suspend fun FriendMessageEvent.onEvent() {
        route(prefix = "", delimiter = " ") {
            exception { throwable ->
                PlainText(throwable.toString())
            }
            case("init", "设置学号") {
                val arg2: Int by cmdArg(0, "学号", it)
                dbCtx {
                    if (it.select().from(User.USER).where(User.USER.ACCOUNT.eq(sender.id)).limit(1).fetch().isNotEmpty) {
                        it.update(User.USER).set(User.USER.STUDENT_ID, arg2).where(User.USER.ACCOUNT.eq(sender.id)).execute()
                        reply(PlainText("设置学号成功"))
                    } else {
                        it.insertInto(User.USER)
                            .columns(
                                User.USER.ACCOUNT,
                                User.USER.BOT,
                                User.USER.STUDENT_ID,
                                User.USER.ENABLE,
                                User.USER.JOIN_TIME
                            ).values(
                                sender.id, sender.bot.id, arg2, 1.toByte(),
                                LocalDateTime.now()
                            ).execute()
                        reply(PlainText("创建账号成功"))
                    }
                }
            }
            case("2", "二号命令") {
                reply("啦啦啦啦")
            }
            case("3", "异常测试") {
                throw IllegalAccessError("2333")
            }
            nextRoute("lalal", "", ::next)
            default {
                reply(PlainText(generateHelp()))

            }

        }

    }

    suspend fun next(route: CommandRouter<FriendMessageEvent>) {
        route.case("dsd") {

            reply("dsd")
        }
        route.default {

            reply(PlainText(route.generateHelp()))
        }
    }


}

