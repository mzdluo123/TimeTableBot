package io.github.mzdluo123.timetablebot.schoolservice

import com.google.gson.JsonParser
import io.github.mzdluo123.timetablebot.config.AppConfig
import io.github.mzdluo123.timetablebot.utils.Dependencies
import io.github.mzdluo123.timetablebot.utils.logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.FormBody
import okhttp3.Request
import org.jsoup.Jsoup
import java.math.BigInteger
import java.security.KeyFactory
import java.security.PublicKey
import java.security.spec.RSAPublicKeySpec
import java.util.Base64
import javax.crypto.Cipher

private val logger = logger()
suspend fun getCsrfTokenV1(): String {
    val rsp = withContext(Dispatchers.IO) {
        Dependencies.okhttp.newCall(
            Request.Builder().url("${AppConfig.getInstance().baseUrl}/jwglxt/xtgl/login_slogin.html?language=zh_CN")
                .build()
        ).execute()
    }

    val document = Jsoup.parse(rsp.body?.string())
    return document.select("#csrftoken").`val`()
}

suspend fun getPublicKeyV1(): PublicKey {
    val rsp = withContext(Dispatchers.IO) {
        Dependencies.okhttp.newCall(
            Request.Builder().url("${AppConfig.getInstance().baseUrl}/jwglxt/xtgl/login_getPublicKey.html")
                .build()
        ).execute()
    }
    val jsonObj = JsonParser().parse(rsp.body?.string())
    val m = BigInteger(Base64.getDecoder().decode(jsonObj.asJsonObject["modulus"].asString))
    val e = BigInteger(Base64.getDecoder().decode(jsonObj.asJsonObject["exponent"].asString))

    return KeyFactory.getInstance("RSA").generatePublic(RSAPublicKeySpec(m, e))
}

suspend fun loginV1(user: String, pwd: String) {
    val csrf = getCsrfTokenV1()
    val key = getPublicKeyV1()
    val encPwd = encrypt(key, pwd)
    val rsp = withContext(Dispatchers.IO) {
        Dependencies.okhttp.newCall(
            Request.Builder().url("${AppConfig.getInstance().baseUrl}/jwglxt/xtgl/login_slogin.html?time=1599304442693").post(
                FormBody.Builder().add("csrftoken", csrf)
                    .add("yhm", user)
                    .add("mm", encPwd)
                    .add("mm", encPwd)
                    .build()
            ).build()
        ).execute()
    }
    val doc = Jsoup.parse(rsp.body?.string())
    val msg = doc.select("#tips").text()
   if (msg != ""){
       throw AuthorizationException(msg)
   }
    logger.info("通过教务系统登录成功")
}

private fun encrypt(key: PublicKey, data: String): String {
    val cipher = Cipher.getInstance("RSA")
    cipher.init(Cipher.ENCRYPT_MODE, key)
    val encrypted = cipher.doFinal(data.encodeToByteArray())
    return Base64.getEncoder().encodeToString(encrypted)
}