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
        val cdId: String = "",
        val cdmc: String = "",  // 教室
        val cxbj: String = "",
        val date: String = "",
        val dateDigit: String = "",
        val dateDigitSeparator: String = "",
        val day: String = "",
        val jc: String = "",
        val jcor: String = "",
        val jcs: String = "",  // 课程长度(节) 比如 5-6
        @SerializedName("jgh_id")
        val jghId: String = "",
        val jgpxzd: String = "",
        @SerializedName("jxb_id")
        val jxbId: String = "",  // 每个课都有的一个id，不知道相同课id是否相同
        val jxbmc: String = "",  // 课程名称
        val jxbsftkbj: String = "",
        @SerializedName("kch_id")
        val kchId: String = "",
        val kcmc: String = "",
        val kcxszc: String = "",
        val khfsmc: String = "",
        val listnav: String = "",
        val localeKey: String = "",
        val month: String = "",
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
        val xf: String = "", // 学分
        val xkbz: String = "",
        val xm: String = "",  // 老师名字
        val xnm: String = "",
        val xqdm: String = "",
        val xqh1: String = "",
        @SerializedName("xqh_id")
        val xqhId: String = "",
        val xqj: String = "",  // 星期
        val xqjmc: String = "",
        val xqm: String = "",
        val xqmc: String = "",
        val xsdm: String = "",
        val year: String = "",
        val zcd: String = "",  // 周 1-10周,13-18周
        val zhxs: String = "",  // 周学时
        val zxs: String = "",  // 总学时
        val zyfxmc: String = "",
        val zzrl: String = ""
    ) {
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

    data class Sjk(
        val date: String = "",
        val dateDigit: String = "",
        val dateDigitSeparator: String = "",
        val day: String = "",
        val jgpxzd: String = "",
        val jsxm: String = "",
        val kcmc: String = "",
        val listnav: String = "",
        val localeKey: String = "",
        val month: String = "",
        val pageable: Boolean = false,
        val qsjsz: String = "",
        val qtkcgs: String = "",
        val queryModel: QueryModel = QueryModel(),
        val rangeable: Boolean = false,
        val rsdzjs: Int = 0,
        val sfsjk: String = "",
        val sjkcgs: String = "",
        val totalResult: String = "",
        val userModel: UserModel = UserModel(),
        val xf: String = "",
        val xksj: String = "",
        val xnmc: String = "",
        val xqmmc: String = "",
        val year: String = ""
    ) {
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

    data class XqjmcMap(
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

    data class Xsxx(
        @SerializedName("KXKXXQ")
        val kXKXXQ: String = "",
        @SerializedName("XH")
        val xH: String = "",
        @SerializedName("XH_ID")
        val xHID: String = "",
        @SerializedName("XKKG")
        val xKKG: String = "",
        @SerializedName("XKKGXQ")
        val xKKGXQ: String = "",
        @SerializedName("XM")
        val xM: String = "",
        @SerializedName("XNM")
        val xNM: String = "",
        @SerializedName("XNMC")
        val xNMC: String = "",
        @SerializedName("XQM")
        val xQM: String = "",
        @SerializedName("XQMMC")
        val xQMMC: String = "",
        @SerializedName("YWXM")
        val yWXM: String = ""
    )
}