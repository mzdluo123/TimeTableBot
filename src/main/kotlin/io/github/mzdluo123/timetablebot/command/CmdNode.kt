package io.github.mzdluo123.timetablebot.command

import kotlin.reflect.KCallable
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.isSubclassOf

interface CmdNode {}

class CmdEndPoint(val obj: BaseCmdController, val endPoint: KCallable<*>, val description: String, val depth: Int) :
    CmdNode

class CmdChildNode(val cmd: String, val next: CmdTree) : CmdNode

class CmdTree : HashMap<String, CmdNode>(), CmdNode {

    fun findEndPoint(cmd: List<String>): CmdEndPoint? {
        var node: CmdNode = this[cmd[0]] ?: return null
        for (i in 1 until cmd.size) {
            if (node is CmdChildNode) {
                node = node.next[cmd[i]] ?: return null
                continue
            }
            if (node is CmdEndPoint) {
                return node
            }
        }
        return null
    }

    fun buildCmdTree(cls: KClass<*>, cmdProcessor: CommandProcessor<*>, depth: Int = 1) {
        if (!cls.isSubclassOf(BaseCmdController::class)) {
            return
        }
        val tree = CmdTree()
        val instance = cls.createInstance() as BaseCmdController
        instance.cmdProcessor = cmdProcessor
        if (this.containsKey(instance.cmd())) {
            return
        }
        this[instance.cmd()] = tree
        val alias = instance.alias()
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
                    tree[mapping.subCmd] = CmdEndPoint(instance, kCallable, mapping.des, depth)
                }
            }

            if (sub != null) {
                val subclass = kCallable.returnType.javaClass.kotlin
                if (BaseCmdController::class.isSubclassOf(subclass)) {
                    val sub = subclass.createInstance() as BaseCmdController
                    val newTree = CmdTree()
                    newTree.buildCmdTree(subclass, cmdProcessor, depth + 1)
                    tree[sub.cmd()] = CmdChildNode(sub.cmd(), newTree)
                }

            }
        }

    }
}