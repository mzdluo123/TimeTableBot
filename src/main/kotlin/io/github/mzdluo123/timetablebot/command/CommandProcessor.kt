package io.github.mzdluo123.timetablebot.command

import kotlinx.coroutines.CompletableDeferred
import net.mamoe.mirai.message.MessageEvent
import kotlin.reflect.KClass
import kotlin.reflect.full.findAnnotation

class CommandProcessor<S : MessageEvent>(
    val rootController: KClass<BaseCmdController>,
    private val prefix: String = "",
    private val delimiter: String = " "
) {
    private val sessionMap = hashMapOf<Long, CompletableDeferred<String>>()
    private val cmdTree = CmdTree()

    init {
        cmdTree.buildCmdTree(rootController, this)
    }


    fun process(source: S) {
        val msg = source.message.contentToString()
        val sender = source.sender.id
        if (sender in sessionMap) {
            sessionMap[sender]?.complete(msg)
            return
        }
        if (!msg.startsWith(prefix)) {
            return
        }

        val params = msg.split(delimiter)
        val endPoint = cmdTree.findEndPoint(params)
        if (endPoint != null) {
            val argList = mutableListOf<Any?>()
            val newParams = params.subList(endPoint.depth, endPoint.depth + endPoint.endPoint.parameters.size)
            var argIndex = 0
            endPoint.endPoint.parameters.forEach {
                when (it.type) {
                    is MessageEvent -> argList.add(source)
                    is CmdParam<*> -> {
                        val desc = it.findAnnotation<ParamDescription>()
                        argList.add(CmdParam<S>(newParams[argIndex], description = desc?.des ?: "暂无描述"))
                        argIndex++
                    }
                    else -> argList.add(null)
                }
            }
            endPoint.endPoint.call(argList)
        }


    }

    fun addUnCompleteSession(user: Long, session: CompletableDeferred<String>) {
        sessionMap[user] = session
    }

    fun delUnCompleteSession(user: Long) {
        sessionMap.remove(user)
    }

}