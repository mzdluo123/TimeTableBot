package io.github.mzdluo123.timetablebot.command

import kotlinx.coroutines.CompletableDeferred
import net.mamoe.mirai.message.MessageEvent

class CommandProcessor<S : MessageEvent>(
    val searchPackage: String = "",
    private val prefix: String = "",
    private val delimiter: String = " "
) {
    init {
        // todo 搜索并导入所有控制器
    }

    private val sessionMap = hashMapOf<Long, CompletableDeferred<String>>()


    fun process(source: S) {
        val msg = source.message.contentToString()
        val sender = source.sender.id
        if (sender in sessionMap){
            sessionMap[sender]?.complete(msg)
            return
        }
        if (!msg.startsWith(prefix)){
            return
        }

        val params = msg.split(delimiter)



    }

    fun addUnCompleteSession(user: Long, session: CompletableDeferred<String>) {
        sessionMap[user] = session
    }

    fun delUnCompleteSession(user: Long) {
        sessionMap.remove(user)
    }

}