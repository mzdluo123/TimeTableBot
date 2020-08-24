package io.github.mzdluo123.timetablebot.command

//@Target(AnnotationTarget.CLASS)
//annotation class CmdController(val cmd: String, val alias: String)


@Target(AnnotationTarget.FUNCTION)
annotation class CmdMapping(val subCmd: String)
