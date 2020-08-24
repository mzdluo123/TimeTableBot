package io.github.mzdluo123.timetablebot.command

import kotlin.reflect.KCallable
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.isSubclassOf


interface CmdNode {}

class CmdEndPoint(val obj: BaseCmdController, val endPoint: KCallable<*>, val description: String, val depth: Int) :
    CmdNode

class CmdTree(val cmd: String, val description: String="") : HashMap<String, CmdNode>(), CmdNode {

    fun findNode(cmd: List<String>): CmdNode? {
        var node: CmdNode = this[cmd[0]] ?: return null
        for (i in 1 until cmd.size) {
            if (node is CmdTree) {
                node = node[cmd[i]] ?: node["*"] ?: return node
                continue
            }
            if (node is CmdEndPoint) {
                return node
            }
        }
        return node
    }

    fun buildCmdTree(cls: KClass<*>, cmdProcessor: CommandProcessor<*>, depth: Int = 1) {
        if (!cls.isSubclassOf(BaseCmdController::class)) {
            return
        }
        val instance = cls.createInstance() as BaseCmdController

        if (this.containsKey(instance.cmd)) {
            return
        }

        val tree = CmdTree(instance.cmd, instance.description)
        this[instance.cmd] = tree
        val alias = instance.alias
        alias?.forEach {
            if (this.containsKey(it)) {
                throw IllegalArgumentException("alias 冲突: $it")
            }
            this[it] = tree
        }


        cls.members.forEach { kCallable ->
            val mapping = kCallable.findAnnotation<CmdMapping>()
            val sub = kCallable.findAnnotation<SubCmd>()

            if (mapping != null) {
                if (!tree.containsKey(mapping.subCmd)) {
                    tree[mapping.subCmd] = CmdEndPoint(instance, kCallable, mapping.des, depth + 1)
                }
            }

            if (sub != null) {
                val subclass = kCallable.returnType.javaClass.kotlin
                if (BaseCmdController::class.isSubclassOf(subclass)) {
                    val sub = subclass.createInstance() as BaseCmdController
                    val newTree = CmdTree(sub.cmd, sub.description)
                    newTree.buildCmdTree(subclass, cmdProcessor, depth + 1)
                    tree[sub.cmd] = newTree
                }

            }
        }

    }

    override fun toString(): String {
        return buildString {
            append("命令：$cmd\n")
            append("描述：$description\n")
            append("子命令如下：\n")
            for (i in keys) {
                append("$i\n")
            }
        }
    }
}