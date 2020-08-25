package io.github.mzdluo123.timetablebot.schoolservice

import io.github.mzdluo123.timetablebot.config.AppConfig
import io.github.mzdluo123.timetablebot.utils.Dependencies
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.FormBody
import okhttp3.Request
import org.bouncycastle.jce.provider.BouncyCastleProvider
import org.jsoup.Jsoup
import java.math.BigInteger
import java.security.KeyFactory
import java.security.PublicKey
import java.security.spec.RSAPublicKeySpec
import javax.crypto.Cipher

data class PK(val modulus: String, val exponent: String)

private suspend fun getPK(): PublicKey {
    //获取public_key
    val reqKey = Request.Builder().get()
        .url("${AppConfig.getInstance().authUrl}/cas/v2/getPubKey")
        .build()
    val resKey = withContext(Dispatchers.IO) { Dependencies.okhttp.newCall(reqKey).execute() }
    val pk = Dependencies.gson.fromJson(resKey.body().string(), PK::class.java)
    val bigIntMod = BigInteger("e1ae70083e0bb9a136552224d6696dc029f6757ae784fce95301bcccef4005562208e08607886b18495d305c8bba0a12559fdf370051bf847c1ee4557b3d61c7",16)
    val bigIntExp = BigInteger(pk.exponent, 10)
    val keySpec = RSAPublicKeySpec(bigIntMod, bigIntExp)
    return KeyFactory.getInstance("RSA").generatePublic(keySpec)
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


suspend fun loginToCAS(user: String, pwd: String) {

    val execution = execution()
    val pk = getPK()

    val cipher = Cipher.getInstance("RSA/None/NoPadding", BouncyCastleProvider())
    cipher.init(Cipher.ENCRYPT_MODE, pk)

    val bytes = pwd.reversed().toByteArray(charset("UTF-8")).toMutableList()

    while (bytes.size % 62 != 0){
        bytes.add(0)
    }

    val bytesPasswd = cipher.doFinal(bytes.toByteArray())
    val encodedPwd =bytesPasswd.joinToString(separator = "") { "%02x".format(it) }

    val result = withContext(Dispatchers.IO) {
        Dependencies.okhttp.newCall(
            Request.Builder().url("${AppConfig.getInstance().authUrl}/cas/login")
                .post(FormBody.Builder()
                    .add("username", user)
                    .add("password",encodedPwd)
                    .add("mobileCode","")
                    .add("execution",execution)
                    .add("authcode","")
                    .add("_eventId","submit")
                    .build()
                ).build()
        ).execute()
    }
    println(result.code())
    val page = Jsoup.parse(result.body().string())
    println(page.title())
    println(page.select("#errormsg > span").text())
}

suspend fun getInfo(){
    val res = withContext(Dispatchers.IO){
        Dependencies.okhttp.newCall(Request.Builder().url("http://newi.nuc.edu.cn/personal-center").build()).execute()
    }
    println(res.body().string())

}