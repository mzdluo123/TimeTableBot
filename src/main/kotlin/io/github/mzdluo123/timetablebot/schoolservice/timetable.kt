package io.github.mzdluo123.timetablebot.schoolservice

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import io.github.mzdluo123.timetablebot.config.AppConfig
import io.github.mzdluo123.timetablebot.data.TimeTableDTO
import io.github.mzdluo123.timetablebot.utils.Dependencies
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.FormBody
import okhttp3.Request
/*学年 学期*/
suspend fun getTimeTable(schoolYear: Int, term: Int): TimeTableDTO? {

    val xqm= arrayListOf<String>("3","12","16")
    if(term<0 && term >2){throw IllegalAccessError("非法学年！")}
    val mainPage = withContext(Dispatchers.IO) {
        Dependencies.okhttp.newCall(
            Request.Builder().url("${AppConfig.getInstance().authUrl}/cas/login?service=${AppConfig.getInstance().baseUrl}/jwglxt/kbcx/xskbcx_cxXskbcxIndex.html").build()
        ).execute()
    }

    println(mainPage.code)
    
    val rsp = withContext(Dispatchers.IO){
        Dependencies.okhttp.newCall(Request.Builder().url("${AppConfig.getInstance().baseUrl}/jwglxt/kbcx/xskbcx_cxXsKb.html?gnmkdm=N2151").post(
            FormBody.Builder()
                .add("xnm",schoolYear.toString())
                .add("xqm",xqm[term])
                .build()
        ).build()).execute()
    }
    val gson=Gson()
    println(rsp.code)
    return gson.fromJson<TimeTableDTO>(rsp.body?.string(),TimeTableDTO::class.java)
}