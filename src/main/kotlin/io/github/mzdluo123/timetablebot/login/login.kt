package io.github.mzdluo123.timetablebot.login

import com.alibaba.fastjson.JSONObject
import com.google.gson.Gson
import okhttp3.*
import java.math.BigInteger
import java.security.KeyFactory
import java.security.spec.RSAPublicKeySpec
import java.util.*
import java.util.regex.Pattern
import javax.crypto.Cipher
fun login(): String {
    val passwd = ""
    val name = ""
    val cookieStore =
        HashMap<String, List<Cookie>>()
    //自动管理cookies
    val client = OkHttpClient.Builder().cookieJar(object : CookieJar {
        override fun saveFromResponse(httpUrl: HttpUrl, list: List<Cookie>) {
            if (httpUrl.host() == "222.31.49.139") {
                cookieStore.put(httpUrl.host(), list)
            }
        }

        override fun loadForRequest(httpUrl: HttpUrl): List<Cookie> {
            val cookies: List<Cookie>? = cookieStore.get(httpUrl.host())
            return cookies ?: ArrayList()
        }
    }).build()

    //获取token
    val reqToken =
        Request.Builder().get().url("http://222.31.49.139/jwglxt/xtgl/login_slogin.html").build()
    val resToken = client.newCall(reqToken).execute()
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
    val resKey = client.newCall(reqKey).execute()
    val key = resKey.body().string()
    val jsonKey: JSONObject = JSONObject.parseObject(key)
    val bigIntMod = BigInteger(1, Base64.getDecoder().decode(jsonKey.getString("modulus")))
    val bigIntExp = BigInteger(1, Base64.getDecoder().decode(jsonKey.getString("exponent")))
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
    val cookie=cookieStore["222.31.49.139"].toString()
    val reqLogin = Request.Builder().post(formBody).addHeader("cookie", cookie)
        .url("http://222.31.49.139/jwglxt/xtgl/login_slogin.html")
        .build()
    val resLogin = client.newCall(reqLogin).execute()
    //获取成绩

    //获取个人信息主页，供测试用
    val reqGrade = Request.Builder().post(formBody)
        .url("http://222.31.49.139/jwglxt/xtgl/index_initMenu.html")
        .build()
    val resGrade = client.newCall(reqGrade).execute()
    println(cookieStore)
    println(resGrade.body().string())

    //关闭
    resToken.close()
    resLogin.close()
    resKey.close()
    resGrade.close()
    return cookie

}