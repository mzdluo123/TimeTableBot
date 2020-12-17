package io.github.mzdluo123.timetablebot.schoolservice

import com.google.gson.Gson
import io.github.mzdluo123.timetablebot.config.AppConfig
import io.github.mzdluo123.timetablebot.data.TimeTableDTO
import io.github.mzdluo123.timetablebot.utils.Dependencies
import io.github.mzdluo123.timetablebot.utils.TTBHttpClient
import io.github.mzdluo123.timetablebot.utils.logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.FormBody
import okhttp3.Request

class UnableGetTimeTableException():Exception()

private val logger = logger()
/*学年 学期*/
suspend fun getTimeTable(client: TTBHttpClient, schoolYear: Int, term: Int): TimeTableDTO? {

    val xqm = arrayListOf<String>("3", "12", "16")
    if (term < 0 || term > 2) {
        throw IllegalAccessError("非法学年！")
    }
   withContext(Dispatchers.IO) {
        client.newCall(
            Request.Builder().url(
                "${AppConfig.getInstance().baseUrl}/jwglxt/kbcx/xskbcx_cxXskbcxIndex.html?gnmkdm=N2151&layout=default&su="
            ).build()
        ).execute()
    }

    val rsp = withContext(Dispatchers.IO) {
        client.newCall(
            Request.Builder().url("${AppConfig.getInstance().baseUrl}/jwglxt/kbcx/xskbcx_cxXsKb.html?gnmkdm=N2151")
                .post(
                    FormBody.Builder()
                        .add("xnm", schoolYear.toString())
                        .add("xqm", xqm[term])
                        .build()
                ).build()
        ).execute()
    }
    val gson = Gson()
    val body = rsp.body?.string() ?: throw UnableGetTimeTableException()
    logger.info("获取课程表成功")
    return gson.fromJson(body, TimeTableDTO::class.java)
}