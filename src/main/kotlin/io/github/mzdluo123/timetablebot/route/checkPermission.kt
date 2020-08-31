package io.github.mzdluo123.timetablebot.route

import io.github.mzdluo123.timetablebot.config.AppConfig
import net.mamoe.mirai.contact.PermissionDeniedException
import net.mamoe.mirai.message.MessageEvent

fun MessageEvent.requireAdminPermission(){
    val senderId = this.sender.id
    if (!AppConfig.getInstance().isAdmin(senderId)){
        throw PermissionDeniedException("你不是机器人管理员，无法使用此命令")
    }
}