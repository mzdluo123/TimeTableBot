package io.github.mzdluo123.timetablebot.data


import com.google.gson.annotations.SerializedName
import io.github.mzdluo123.timetablebot.config.AppConfig
import io.github.mzdluo123.timetablebot.utils.Dependencies
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Request
import org.jsoup.Jsoup
import java.io.File

data class Hitokoto(
    @SerializedName("commit_from")
    val commitFrom: String = "",
    @SerializedName("created_at")
    val createdAt: String = "",
    val creator: String = "",
    @SerializedName("creator_uid")
    val creatorUid: Int = 0,
    val from: String = "",
    @SerializedName("from_who")
    val fromWho: Any? = null,
    val hitokoto: String = "",
    val id: Int = 0,
    val length: Int = 0,
    val reviewer: Int = 0,
    val type: String = "",
    val uuid: String = ""
)

//suspend fun getHitokoto(): Hitokoto? {
//    val rsp = withContext(Dispatchers.IO) {
//        Dependencies.okhttp.newCall(Request.Builder().url("https://v1.alapi.cn/api/hitokoto?format=text").build()).execute()
//    }
//    return Dependencies.gson.fromJson(rsp.body?.string(), Hitokoto::class.java)
//}
suspend fun getHitokoto(): String? {
    val rsp = withContext(Dispatchers.IO) {
        Dependencies.okhttp.newCall(Request.Builder().url("https://v1.alapi.cn/api/hitokoto?format=text").build()).execute()
    }
    return rsp.body?.string()
}

suspend fun main() {
    AppConfig.loadConfig(File("config.yml"))
    println(getHitokoto())
}