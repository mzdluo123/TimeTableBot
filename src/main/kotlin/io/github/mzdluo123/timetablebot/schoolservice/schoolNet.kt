package io.github.mzdluo123.timetablebot.schoolservice

import io.github.mzdluo123.timetablebot.config.AppConfig
import io.github.mzdluo123.timetablebot.data.SchoolNetInterfaceInfo
import io.github.mzdluo123.timetablebot.data.SchoolNetTotalInfoDTO
import io.github.mzdluo123.timetablebot.utils.Dependencies
import io.github.mzdluo123.timetablebot.utils.TTBHttpClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.FormBody
import okhttp3.Request

//suspend fun getSchoolNetTotalInfo(): SchoolNetTotalInfoDTO? {
//    val rep = withContext(Dispatchers.IO) {
//        Dependencies.okhttp.newCall(
//            Request.Builder().url(AppConfig.getInstance().schoolNet.url + "/getLeftUpAreaData").post(FormBody.Builder().build()).build()
//        ).execute()
//    }
//    return Dependencies.gson.fromJson(rep.body?.string(), SchoolNetTotalInfoDTO::class.java)
//
//}

suspend fun getSchoolNetInterfaceInfo(client: TTBHttpClient): SchoolNetInterfaceInfo? {
    val rep = withContext(Dispatchers.IO) {
        client.newCall(
            Request.Builder().url(AppConfig.getInstance().schoolNet.url + "/getExportFlowData").post(FormBody.Builder().build()).build()
        ).execute()
    }
    return Dependencies.gson.fromJson(rep.body?.string(), SchoolNetInterfaceInfo::class.java)

}