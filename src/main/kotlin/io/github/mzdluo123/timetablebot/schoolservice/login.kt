package io.github.mzdluo123.timetablebot.schoolservice


import com.google.gson.JsonParser
import io.github.mzdluo123.timetablebot.config.AppConfig
import io.github.mzdluo123.timetablebot.utils.Dependencies
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.FormBody
import okhttp3.Request
import okhttp3.RequestBody
import java.math.BigInteger
import java.security.KeyFactory
import java.security.spec.RSAPublicKeySpec
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.regex.Pattern
import javax.crypto.Cipher
import kotlin.collections.ArrayList

class FaildLogin : Exception {
    constructor() {}
    constructor(message: String) : super(message)
}
suspend fun login(user:String,pwd:String) {
    val url="${AppConfig.getInstance().baseUrl}/jwglxt/xtgl/login_slogin.html"
    val reqToken = Request.Builder().get().url(url).build()
    val resToken = withContext(Dispatchers.IO){ Dependencies.okhttp.newCall(reqToken).execute()}
    val index_html = resToken.body().string()
    val pattern = "name=\"csrftoken\" value=\"(.*?)\""
    val pat = Pattern.compile(pattern)
    val mat = pat.matcher(index_html)
    mat.find()
    val cstf_token = mat.group(1)

    //获取public_key

    //获取public_key
    val reqKey = Request.Builder().get()
        .url("${AppConfig.getInstance().baseUrl}/jwglxt/xtgl/login_getPublicKey.html")
        .build()
    val resKey = withContext(Dispatchers.IO){ Dependencies.okhttp.newCall(reqKey).execute()}
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
    val bytesPasswd = cipher.doFinal(pwd.toByteArray(charset("UTF-8")))
    val bytesPasswdToBase64 = Base64.getEncoder().encode(bytesPasswd)
    val mm = String(bytesPasswdToBase64)

    //登录
    val formBody: RequestBody = FormBody.Builder()
        .add("csrftoken", cstf_token)
        .add("yhm", user)
        .add("mm", mm)
        .build()

    val reqLogin = Request.Builder().post(formBody)
        .url("${AppConfig.getInstance().baseUrl}/jwglxt/xtgl/login_slogin.html")
        .build()
    val resLogin =  withContext(Dispatchers.IO){Dependencies.okhttp.newCall(reqLogin).execute()}

    // todo 登录成功检查，未成功抛出异常

//    //获取成绩
//    //供测试是否登录成功用
    val reqGrade = Request.Builder().post(formBody)
        .url("${AppConfig.getInstance().baseUrl}/jwglxt/xtgl/index_initMenu.html")
        .build()
    val html= withContext(Dispatchers.IO){Dependencies.okhttp.newCall(reqGrade).execute()}.body().string()
    val errors= listOf("用户名或密码不正确，请重新输入！","用户名不能为空！")
    for (i in errors) {
        val error =i.toRegex()
        if (error.find(html)!=null) throw FaildLogin(i)
    }
    //匹配是否被莫名其妙打回login页
    val patterns=
           "<input id=\"send_error\" type=\"hidden\" value=\"loginView.sendsms.error \"/>".toRegex()
    if (patterns.find(html)!=null) throw FaildLogin("因未知原因导致登陆失败！")
    val bool=patterns.find(html)
    resToken.close()
    resLogin.close()
    resKey.close()


}
