package io.github.mzdluo123.timetablebot.login


import com.google.gson.JsonParser
import io.github.mzdluo123.timetablebot.utils.Dependencies
import okhttp3.FormBody
import okhttp3.Request
import okhttp3.RequestBody
import java.math.BigInteger
import java.security.KeyFactory
import java.security.spec.RSAPublicKeySpec
import java.util.*
import java.util.regex.Pattern
import javax.crypto.Cipher

fun login(): String {
    val passwd = ""
    val name = ""

    //自动管理cookies

    //获取token
    val reqToken = Request.Builder().get().url("http://222.31.49.139/jwglxt/xtgl/login_slogin.html").build()
    val resToken = Dependencies.okhttp.newCall(reqToken).execute()
    val index_html = resToken.body().string()
    val pattern = "name=\"csrftoken\" value=\"(.*?)\""
    val pat = Pattern.compile(pattern)
    val mat = pat.matcher(index_html)
    mat.find()
    val cstf_token = mat.group(1)

    //获取public_key

    //获取public_key
    val reqKey = Request.Builder().get()
        .url("http://222.31.49.139/jwglxt/xtgl/login_getPublicKey.html")
        .build()
    val resKey = Dependencies.okhttp.newCall(reqKey).execute()
    val key = resKey.body().string()
    val jsonKey = JsonParser().parse(key)
    val bigIntMod = BigInteger(1, Base64.getDecoder().decode(jsonKey.asJsonObject["modulus"].asString))
    val bigIntExp = BigInteger(1, Base64.getDecoder().decode(jsonKey.asJsonObject["exponent"].asString))
    val keySpec = RSAPublicKeySpec(bigIntMod, bigIntExp)
    val rsa = KeyFactory.getInstance("RSA")
    val publicKey = rsa.generatePublic(keySpec)

    //加密密码

    //加密密码
    val cipher = Cipher.getInstance("RSA")
    cipher.init(Cipher.ENCRYPT_MODE, publicKey)
    val bytesPasswd = cipher.doFinal(passwd.toByteArray(charset("UTF-8")))
    val bytesPasswdToBase64 = Base64.getEncoder().encode(bytesPasswd)
    val mm = String(bytesPasswdToBase64)

    //登录

    //登录
    val formBody: RequestBody = FormBody.Builder()
        .add("csrftoken", cstf_token)
        .add("yhm", name)
        .add("mm", mm)
        .build()

    val reqLogin = Request.Builder().post(formBody)
        .url("http://222.31.49.139/jwglxt/xtgl/login_slogin.html")
        .build()
    val resLogin = Dependencies.okhttp.newCall(reqLogin).execute()
    //获取成绩

    //获取个人信息主页，供测试用
    val reqGrade = Request.Builder().post(formBody)
        .url("http://222.31.49.139/jwglxt/xtgl/index_initMenu.html")
        .build()
    val resGrade = Dependencies.okhttp.newCall(reqGrade).execute()

    println(resGrade.body().string())

    //关闭
    resToken.close()
    resLogin.close()
    resKey.close()
    resGrade.close()

    return ""
}