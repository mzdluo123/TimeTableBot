package io.github.mzdluo123.timetablebot.data


import com.google.gson.annotations.SerializedName

data class SchoolNetTotalInfoDTO(
    val activeUserCount: Int = 0,  // 活跃用户
    val android: Int = 0,
    val etcUserCount: Int = 0,
    val freeUserCount: Int = 0,
    val ios: Int = 0,
    val lostUserCount: Int = 0,
    val mobileUserCount: Int = 0,
    val other: Int = 0,
    val pc: Int = 0,
    val pcUserCount: Int = 0,
    val registerUserCount: Int = 0,
    val totalOnlineUserCount: Int = 0,
    val wireUserCount: Int = 0,  // 有线在线人数
    val wireless1X: Int = 0,
    val wirelessPortal: Int = 0,
    val wirelessUserCount: Int = 0  // 无线在线人数
)


class SchoolNetInterfaceInfo : ArrayList<SchoolNetInterfaceInfo.SchoolNetInterfaceInfoItem>(){
    data class SchoolNetInterfaceInfoItem(
        val divId: String = "",
        val downStream: Double = 0.0,
        val graph: Graph = Graph(),
        val maxBandWidth: Long = 0,
        val name: String = "",
        val upStream: Double = 0.0
    ) {
        data class Graph(
            val `data`: List<Data> = listOf(),
            val subitemLabels: List<String> = listOf(),
            val templateName: String = "",
            val title: String = "",
            val xAxis: List<String> = listOf()
        ) {
            data class Data(
                val `data`: List<String> = listOf(),
                val name: String = "",
                val type: String = ""
            )
        }
    }
}