package io.github.mzdluo123.timetablebot.data


import com.google.gson.annotations.SerializedName
import io.github.mzdluo123.timetablebot.config.AppConfig
import io.github.mzdluo123.timetablebot.utils.Dependencies
import io.github.mzdluo123.timetablebot.utils.TTBHttpClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Request
import java.io.File

data class Poem(
        @SerializedName("author")
        val author: Author,
        @SerializedName("code")
        val code: Int,
        @SerializedName("data")
        val `data`: Data,
        @SerializedName("msg")
        val msg: String
) {
    data class Author(
            @SerializedName("desc")
            val desc: String,
            @SerializedName("name")
            val name: String
    )

    data class Data(
            @SerializedName("author")
            val author: String,
            @SerializedName("category")
            val category: String,
            @SerializedName("content")
            val content: String,
            @SerializedName("origin")
            val origin: String
    )
}

suspend fun getPoem(client:TTBHttpClient): Poem {
    val rsp = withContext(Dispatchers.IO) {
        client.newCall(Request.Builder().url("https://v1.alapi.cn/api/shici?type=shuqing").build()).execute()
    }
    return Dependencies.gson.fromJson<Poem>(rsp.body?.string(), Poem::class.java)
}

//suspend fun getHitokoto(): String? {
//    val fromBody = FormBody.Builder() .build()
//    val rsp = withContext(Dispatchers.IO) {
//        Dependencies.okhttp.newCall(Request.Builder().url("https://v2.jinrishici.com/sentence")
//                .post(fromBody)
//                .build()).execute()
//    }
//    return rsp.body?.string()
//}

suspend fun main() {
    AppConfig.loadConfig(File("config.yml"))
    println(getPoem(TTBHttpClient()))
}