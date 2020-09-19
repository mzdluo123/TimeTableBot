package io.github.mzdluo123.timetablebot.data


import com.google.gson.annotations.SerializedName

data class TimeTableDTO(
    val jxhjkcList: List<Any> = listOf(),
    val kbList: List<Kb> = listOf(),  // 必修课
    val kblx: Int = 0,
    val sjkList: List<Sjk> = listOf(),  // 实践课
    val xkkg: Boolean = false,
    val xqbzxxszList: List<Any> = listOf(),
    val xqjmcMap: XqjmcMap = XqjmcMap(),
    val xsbjList: List<Any> = listOf(),
    val xskbsfxstkzt: String = "",
    val xsxx: Xsxx = Xsxx()  // 学生信息
) {
    data class Kb(
        @SerializedName("cd_id")
        val cdId: String = "",   // 课表ID（唯一），比如 96F797CCBF56AA73E050007F01000B90
        val cdmc: String = "",   // 教室
        val cxbj: String = "",
        val date: String = "",   // 当前时间（查询的日期）,比如 二○二○年八月二十六日
        val dateDigit: String = "",             // 当前时间，比如 2020年8月26日
        val dateDigitSeparator: String = "",    // 同上，比如 2020-8-26
        val day: String = "",  // 日 比如 26
        val jc: String = "",   // 当前课程上课时间，比如1-2节
        val jcor: String = "", // 课程长度（节），比如5-6
        val jcs: String = "",  // 课程长度（节），比如5-6
        @SerializedName("jgh_id")
        val jghId: String = "", // 教工号ID（唯一），在一张表里id相同，比如1120031
        val jgpxzd: String = "",
        @SerializedName("jxb_id")
        val jxbId: String = "", // 课程教学部id，可能是加密过的，比如99F148E6D1E5F062E050007F010001B5
        val jxbmc: String = "", // 课程教学部名称
        val jxbsftkbj: String = "",
        @SerializedName("kch_id")
        val kchId: String = "",  // 课程id，同课程此id相同
        val kcmc: String = "",   // 课程名
        val kcxszc: String = "", // 学时，比如 理论学时:88
        val khfsmc: String = "", // 考核方式,比如 考试或者未安排
        val listnav: String = "",
        val localeKey: String = "", // 语言，比如 zh_CN
        val month: String = "",     // 开课月份,比如 8
        val oldjc: String = "",
        val oldzc: String = "",
        val pageable: Boolean = false,
        val pkbj: String = "",
        val queryModel: QueryModel = QueryModel(),
        val rangeable: Boolean = false,
        val rsdzjs: Int = 0,
        val sxbj: String = "",
        val totalResult: String = "",
        val userModel: UserModel = UserModel(),
        val xf: String = "",    // 学分
        val xkbz: String = "",  // 选课备注
        val xm: String = "",    // 老师名字
        val xnm: String = "",   // 学年，比如2019，和下面的year构成2019-2020学年
        val xqdm: String = "",
        val xqh1: String = "",
        @SerializedName("xqh_id")
        val xqhId: String = "",
        val xqj: String = "",   // 星期 比如 1
        val xqjmc: String = "", // 星期 比如星期一
        val xqm: String = "",   // 学期末(月） 比如12
        val xqmc: String = "",  // 比如 本部
        val xsdm: String = "",
        val year: String = "", // 学年 比如 2020
        val zcd: String = "",  // 周 1-10周,13-18周
        val zhxs: String = "",  // 周学时
        val zxs: String = "",  // 总学时
        val zyfxmc: String = "", // 专业方向
        val zzrl: String = ""
    ) {
        data class QueryModel(   // 感觉这个对本项目基本没有任何卵用
            val currentPage: Int = 0,
            val currentResult: Int = 0,
            val entityOrField: Boolean = false,
            val limit: Int = 0,
            val offset: Int = 0,
            val pageNo: Int = 0,
            val pageSize: Int = 0,
            val showCount: Int = 0,
            val sorts: List<Any> = listOf(),
            val totalCount: Int = 0,
            val totalPage: Int = 0,
            val totalResult: Int = 0
        )

        data class UserModel( // 这个也没什么卵用
            val monitor: Boolean = false,
            val roleCount: Int = 0,
            val roleKeys: String = "",
            val roleValues: String = "",
            val status: Int = 0,
            val usable: Boolean = false
        )
    }

    data class Sjk(  // 实践课
        val date: String = "",
        val dateDigit: String = "",
        val dateDigitSeparator: String = "",
        val day: String = "",
        val jgpxzd: String = "",
        val jsxm: String = "", // 老师
        val kcmc: String = "", // 课程名字
        val listnav: String = "",
        val localeKey: String = "",
        val month: String = "",// 开课月
        val pageable: Boolean = false,
        val qsjsz: String = "", // 时间 比如 1-8周
        val qtkcgs: String = "", // 其他课程概述 比如 程序设计基础（2）雷海卫(共8周)/1-8周/无 做id
        val queryModel: QueryModel = QueryModel(),
        val rangeable: Boolean = false,
        val rsdzjs: Int = 0,
        val sfsjk: String = "",
        val sjkcgs: String = "",
        val totalResult: String = "",
        val userModel: UserModel = UserModel(),
        val xf: String = "",   //学分
        val xksj: String = "", // 下课时间 比如 2020-05-08 18:19:48 （应该指的是结课时间）
        val xnmc: String = "", // 学年 比如 2019-2020
        val xqmmc: String = "",
        val year: String = ""
    ) {
        // 下面是没什么用系列
        data class QueryModel(
            val currentPage: Int = 0,
            val currentResult: Int = 0,
            val entityOrField: Boolean = false,
            val limit: Int = 0,
            val offset: Int = 0,
            val pageNo: Int = 0,
            val pageSize: Int = 0,
            val showCount: Int = 0,
            val sorts: List<Any> = listOf(),
            val totalCount: Int = 0,
            val totalPage: Int = 0,
            val totalResult: Int = 0
        )

        data class UserModel(
            val monitor: Boolean = false,
            val roleCount: Int = 0,
            val roleKeys: String = "",
            val roleValues: String = "",
            val status: Int = 0,
            val usable: Boolean = false
        )
    }

    data class XqjmcMap( // 星期12345
        @SerializedName("1")
        val x1: String = "",
        @SerializedName("2")
        val x2: String = "",
        @SerializedName("3")
        val x3: String = "",
        @SerializedName("4")
        val x4: String = "",
        @SerializedName("5")
        val x5: String = ""
    )

    data class Xsxx( // 学生信息
        @SerializedName("KXKXXQ")
        val kXKXXQ: String = "",
        @SerializedName("XH") // 学号
        val xH: String = "",
        @SerializedName("XH_ID") // 学号
        val xHID: String = "",
        @SerializedName("XKKG")
        val xKKG: String = "",
        @SerializedName("XKKGXQ")
        val xKKGXQ: String = "",
        @SerializedName("XM") //姓名
        val xM: String = "",
        @SerializedName("XNM")
        val xNM: String = "", // 学年 比如2019
        @SerializedName("XNMC") // 学年 比如2019-2020
        val xNMC: String = "",
        @SerializedName("XQM")
        val xQM: String = "",
        @SerializedName("XQMMC")
        val xQMMC: String = "",
        @SerializedName("YWXM")
        val yWXM: String = ""
    )
}