package io.github.mzdluo123.timetablebot.command

import io.github.mzdluo123.timetablebot.bots.BotsManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

abstract class BaseCmdController(var cmdProcessor: CommandProcessor<*>) : CoroutineScope {
    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = BotsManager.coroutineContext + job

    abstract fun cmd(): String
    abstract fun alias(): List<String>?
}