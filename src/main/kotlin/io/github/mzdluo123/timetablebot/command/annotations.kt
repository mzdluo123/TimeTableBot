package io.github.mzdluo123.timetablebot.command

//@Target(AnnotationTarget.CLASS)
//annotation class CmdController(val cmd: String, val alias: String)


@Target(AnnotationTarget.FUNCTION)
annotation class CmdMapping(val subCmd: String, val des: String ="暂无描述")

@Target(AnnotationTarget.FUNCTION)
annotation class SubCmd()


@Target(AnnotationTarget.VALUE_PARAMETER)
annotation class ParamDescription(val des: String)

