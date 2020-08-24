package io.github.mzdluo123.timetablebot.command

import kotlinx.coroutines.CompletableDeferred
import net.mamoe.mirai.message.MessageEvent
import kotlin.reflect.KClass
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.isSupertypeOf
import kotlin.reflect.full.starProjectedType

class CommandProcessor<S : MessageEvent>(
    val controllers: List<KClass<*>>,
    private val prefix: String = "",
    private val delimiter: String = " "
) {
    private val sessionMap = hashMapOf<Long, CompletableDeferred<String>>()
    private val cmdTree = CmdTree("*")

    init {
        controllers.forEach {
            cmdTree.buildCmdTree(it, this)
        }
    }


    suspend fun process(source: S) {
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
        when (val endPoint = cmdTree.findNode(params)) {
            is CmdEndPoint -> {
                val argList = mutableListOf<Any?>()
                val newParams = params.subList(endPoint.depth, params.size)
                var argIndex = 0
                endPoint.endPoint.parameters.forEach {
                    val type = it.type
                    if (BaseCmdController::class.starProjectedType.isSupertypeOf(type)) {
                        argList.add(endPoint.obj)
                        return@forEach
                    }

                    if (MessageEvent::class.starProjectedType.isSupertypeOf(type)) {
                        argList.add(source)
                        return@forEach
                    }
                    if (CommandProcessor::class.starProjectedType.isSupertypeOf(type)) {
                        argList.add(this)
                        return@forEach
                    }
                    if (CmdParam::class.starProjectedType.isSupertypeOf(type)) {
                        val desc = it.findAnnotation<ParamDescription>()
                        if (argIndex >= newParams.size) {
                            argList.add(CmdParam<S>(null, description = desc?.des ?: "暂无描述"))
                            argIndex++
                            return@forEach
                        }
                        argList.add(CmdParam<S>(newParams[argIndex], description = desc?.des ?: "暂无描述"))
                        argIndex++
                        return@forEach
                    }

                    argList.add(null)

                }
                endPoint.endPoint.call(args = argList.toTypedArray())

            }
            is CmdTree -> {
                source.reply("命令不存在 $endPoint")

            }
        }


    }

    fun addUnCompleteSession(user: Long, session: CompletableDeferred<String>) {
        sessionMap[user] = session
    }

    fun delUnCompleteSession(user: Long) {
        sessionMap.remove(user)
    }

}