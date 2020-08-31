package io.github.mzdluo123.timetablebot.controller


import io.github.mzdluo123.timetablebot.config.AppConfig
import io.github.mzdluo123.timetablebot.gen.timetable.tables.daos.UserDao
import io.github.mzdluo123.timetablebot.gen.timetable.tables.pojos.User
import io.github.mzdluo123.timetablebot.route.CommandRouter
import io.github.mzdluo123.timetablebot.route.cmdArg
import io.github.mzdluo123.timetablebot.route.requireAdminPermission
import io.github.mzdluo123.timetablebot.route.route
import io.github.mzdluo123.timetablebot.task.SyncRequest
import io.github.mzdluo123.timetablebot.task.SyncTask
import io.github.mzdluo123.timetablebot.utils.createDao
import net.mamoe.mirai.event.EventHandler
import net.mamoe.mirai.message.FriendMessageEvent
import net.mamoe.mirai.message.data.PlainText
import java.time.LocalDateTime


class BotMsgListener : BaseListeners() {
    private val userDao = createDao(UserDao::class)

    @EventHandler
    suspend fun FriendMessageEvent.onEvent() {
        val user = userDao.fetchOneByAccount(sender.id)
        route(prefix = "", delimiter = " ") {
            exception { throwable ->
                PlainText(throwable.toString())
            }
            case("init", "设置学号") {
                val arg2: Int by cmdArg(0, "学号", it)
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
            case("sync", "从教务系统同步课程表") {

                if (user == null) {
                    reply("你没有创建账号，请使用init创建账户")
                    return@case
                }
                val arg: String by cmdArg(0, "密码", it)
                SyncTask.requestSync(SyncRequest(user.id, arg))
                reply("我们将在后台刷新你的课程表，完成后会向你发送信息，请稍后\n同步较慢，请勿重复提交")

            }
            case("3", "异常测试") {
                throw IllegalAccessError("2333")
            }
            nextRoute("admin", "管理中心", ::admin)
            default {

                reply(PlainText(generateHelp()))

            }

        }

    }

    private suspend fun admin(route: CommandRouter<FriendMessageEvent>) {

        route.exception {
           PlainText(it.toString())
        }
        route.event.requireAdminPermission()
        route.case("reload","重载配置") {
            AppConfig.reload()
            reply("配置重载成功!")
        }
        route.default {

            reply(PlainText(route.generateHelp()))
        }
    }


}

