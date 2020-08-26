package io.github.mzdluo123.timetablebot.schoolservice

import com.google.gson.JsonParser
import io.github.mzdluo123.timetablebot.config.AppConfig
import io.github.mzdluo123.timetablebot.utils.Dependencies
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.FormBody
import okhttp3.Request
import org.jsoup.Jsoup

data class PK(val modulus: String, val exponent: String)

private suspend fun getPublicKey(): PK {
    //获取public_key
    val reqKey = Request.Builder().get()
        .url("${AppConfig.getInstance().authUrl}/cas/v2/getPubKey")
        .build()
    val resKey = withContext(Dispatchers.IO) { Dependencies.okhttp.newCall(reqKey).execute() }
    //    val bigIntMod = BigInteger(pk.modulus,16)
//    val bigIntExp = BigInteger(pk.exponent, 10)
//    val keySpec = RSAPublicKeySpec(bigIntMod, bigIntExp)
//    return KeyFactory.getInstance("RSA").generatePublic(keySpec)
    //  return RSAUtils.getRSAPublicKey(pk.modulus, "%02x".format(pk.exponent.toInt()))
    return Dependencies.gson.fromJson(resKey.body().string(), PK::class.java)
}


private suspend fun execution(): String {
    val mainPage = withContext(Dispatchers.IO) {
        Dependencies.okhttp.newCall(
            Request.Builder().url("${AppConfig.getInstance().authUrl}/cas/login").build()
        ).execute().body().string()
    }
    val page = Jsoup.parse(mainPage)
    return page.select("input[name=execution]").`val`()
}


private suspend fun encryptPwd(publicKey: PK, pwd: String): String? {
    val rep = withContext(Dispatchers.IO) {
        Dependencies.okhttp.newCall(
            Request.Builder().url("https://whispering-furry-charger.glitch.me/encode").post(
                FormBody.Builder()
                    .add("e", publicKey.exponent)
                    .add("m", publicKey.modulus)
                    .add("data", pwd)
                    .build()
            ).build()
        ).execute()
    }
    if (rep.isSuccessful){
        return JsonParser().parse(rep.body().string()).asJsonObject["encoded"].asString
    }else{
        throw IllegalStateException("无法加密密码: ${rep.message()}")
    }
}

suspend fun loginToCAS(user: String, pwd: String) {
    val pk = getPublicKey()
    val execution = execution()


//    val cipher = Cipher.getInstance("RSA/None/NoPadding", BouncyCastleProvider())
//    cipher.init(Cipher.ENCRYPT_MODE, pk)
//
//    val bytes = pwd.toByteArray(charset("UTF-8")).toMutableList()

    //  val encodedPwd = bytesPasswd.joinToString(separator = "") { "%02x".format(it) }

    val encodedPwd = encryptPwd(pk,pwd.reversed())
    val result = withContext(Dispatchers.IO) {
        Dependencies.okhttp.newCall(
            Request.Builder().url("${AppConfig.getInstance().authUrl}/cas/login")
                .post(
                    FormBody.Builder()
                        .add("username", user)
                        .add("password", encodedPwd)
                        .add("mobileCode", "")
                        .add("execution", execution)
                        .add("authcode", "")
                        .add("_eventId", "submit")
                        .build()
                ).build()
        ).execute()
    }
    println(result.code())
    val page = Jsoup.parse(result.body().string())
    println(page.title())
    println(page.select("#errormsg > span").text())
    println(page.html())
}

suspend fun getInfo() {
    val res = withContext(Dispatchers.IO) {
        Dependencies.okhttp.newCall(Request.Builder().url("http://newi.nuc.edu.cn/personal-center").build()).execute()
    }
    println(res.body().string())

}