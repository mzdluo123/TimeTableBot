package io.github.mzdluo123.timetablebot.command

import io.github.mzdluo123.timetablebot.bots.BotsManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

abstract class BaseCmdController(val cmd: String, val alias: List<String>? = null, val description: String = "") :
    CoroutineScope {
     lateinit var cmdProcessor: CommandProcessor<*>
    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = BotsManager.coroutineContext + job

}