package io.github.mzdluo123.timetablebot.login

import com.alibaba.fastjson.JSON
import com.google.gson.JsonParser
import io.ktor.util.encodeBase64
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonArray
import okhttp3.*
import java.io.IOException
val public_key_url = "http://222.31.49.139/jwglxt/xtgl/login_getPublicKey.html"
val index_url = "http://222.31.49.139/jwglxt/xtgl/login_slogin.html"
val test_url = "http://222.31.49.139/jwglxt/xtgl/index_cxBczjsygnmk.html"
fun login(csrfToken:String){
    /*
    * csrftoken后发一个post过去
    * */

}
fun main() {

    val name=1907040334
    val password="meimima123"
    val request=Request.Builder()
    val session=request.get().url(index_url).build()
    val client=OkHttpClient()
    client.newCall(session).enqueue(object :Callback{
        override fun onFailure(call: Call?, e: IOException?) {
            TODO("Not yet implemented")
        }
        override fun onResponse(call: Call?, response: Response?) {
            if (response?.isSuccessful!!){
                val htmlIndex=response.body().string()
                val re="name=\"csrftoken\" value=\"(.*?)\"".toRegex()
                val csrf_token=re.find(htmlIndex)?.value//获取到登录界面的token
            }
        }
    })
    val public_key_dict=request.get().url(public_key_url).build().body().toString()
}