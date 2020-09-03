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

/**
 * 不要尝试更改这里的任何一个代码，尽管它看起来很奇怪
 * 反正就是这样，他能跑
 * 如果你修改了任何一个地方他可能就不能跑了
 *
 * */

class AuthorizationException(override val message: String) : Exception(message)
class EncryptionFailed(override val message: String) : Exception(message)
data class PK(val modulus: String, val exponent: String)

private suspend fun getPublicKey(): PK {
    //获取public_key
    val reqKey = Request.Builder().get()
        .url("${AppConfig.getInstance().authUrl}/cas/v2/getPubKey")
        .build()
    val resKey = withContext(Dispatchers.IO) { Dependencies.okhttp.newCall(reqKey).execute() }
    return Dependencies.gson.fromJson(resKey.body?.string(), PK::class.java)
}


private suspend fun execution(): String {
    val mainPage = withContext(Dispatchers.IO) {
        Dependencies.okhttp.newCall(
            Request.Builder().url("${AppConfig.getInstance().authUrl}/cas/login").build()
        ).execute().body?.string()
    }
    val page = Jsoup.parse(mainPage)
    return page.select("input[name=execution]").`val`()
}


private suspend fun encryptPwd(publicKey: PK, pwd: String): String? {
    val rep = withContext(Dispatchers.IO) {
        Dependencies.okhttp.newCall(
            Request.Builder().url("https://whispering-furry-charger.glitch.me/encode").post(  // 部署的在线服务，源码在/rsa
                FormBody.Builder()
                    .add("e", publicKey.exponent)
                    .add("m", publicKey.modulus)
                    .add("data", pwd)
                    .build()
            ).build()
        ).execute()
    }
    if (rep.isSuccessful) {
        return JsonParser().parse(rep.body?.string()).asJsonObject["encoded"].asString
    } else {
        throw IllegalStateException("无法加密密码: ${rep.message}")
    }
}

suspend fun loginToCAS(user: String, pwd: String) {
    val pk = getPublicKey()
    val execution = execution()
    var encodedPwd: String? = ""
    for (tryNum in 1..4) {
        try {
            encodedPwd = encryptPwd(pk, pwd.reversed())
            break
        } catch (e: Exception) {
            if (tryNum > 3) {
                throw EncryptionFailed("加密失败")
            }
            logger().info("密码加密失败，正在进行第$tryNum 次尝试")
        }
    }

    val result = withContext(Dispatchers.IO) {
        Dependencies.okhttp.newCall(
            Request.Builder().url("${AppConfig.getInstance().authUrl}/cas/login")
                .post(
                    FormBody.Builder()
                        .add("username", user)
                        .add("password", encodedPwd!!)
                        .add("mobileCode", "")
                        .add("execution", execution)
                        .add("authcode", "")
                        .add("_eventId", "submit")
                        .build()
                ).build()
        ).execute()
    }
    if (result.isSuccessful) {
        val page = Jsoup.parse(result.body?.string())
        if ("统一身份认证平台" in page.title()) {
            throw AuthorizationException(page.select("#errormsg > span").text())
        }
        logger().info("个人门户登录成功！")
    }

}
