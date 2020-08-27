package io.github.mzdluo123.timetablebot.controller


import io.github.mzdluo123.timetablebot.gen.timetable.tables.daos.UserDao
import io.github.mzdluo123.timetablebot.gen.timetable.tables.pojos.User
import io.github.mzdluo123.timetablebot.route.CommandRouter
import io.github.mzdluo123.timetablebot.route.cmdArg
import io.github.mzdluo123.timetablebot.route.route
import io.github.mzdluo123.timetablebot.utils.createDao
import io.github.mzdluo123.timetablebot.utils.dbCtx
import net.mamoe.mirai.event.EventHandler
import net.mamoe.mirai.message.FriendMessageEvent
import net.mamoe.mirai.message.data.PlainText
import java.time.LocalDateTime


class BotMsgListener : BaseListeners() {
    private val userDao = createDao(UserDao::class)

    @EventHandler
    suspend fun FriendMessageEvent.onEvent() {
        route(prefix = "", delimiter = " ") {
            exception { throwable ->
                PlainText(throwable.toString())
            }
            case("init", "设置学号") {
                val arg2: Int by cmdArg(0, "学号", it)
                dbCtx {
                    val user = userDao.fetchOneByAccount(sender.id)
                    if (user != null) {
                        userDao.update(user.apply {
                            studentId = arg2
                        })
                        reply(PlainText("设置学号成功"))
                    } else {
                        userDao.insert(User().apply {
                            account = sender.id
                            bot = sender.bot.id
                            joinTime = LocalDateTime.now()
                            enable = 1.toByte()
                            studentId = arg2
                        })
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

