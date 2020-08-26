package io.github.mzdluo123.timetablebot.schoolservice

import io.github.mzdluo123.timetablebot.config.AppConfig
import io.github.mzdluo123.timetablebot.utils.Dependencies
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.FormBody
import okhttp3.Request

suspend fun getTimeTable(schoolYear: Int, term: Int) {

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
                .add("xqm",term.toString())
                .build()
        ).build()).execute()
    }
    println(rsp.code)
}