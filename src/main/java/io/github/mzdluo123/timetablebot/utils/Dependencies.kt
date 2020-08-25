package io.github.mzdluo123.timetablebot.utils

import com.google.gson.Gson
import io.github.mzdluo123.timetablebot.config.AppConfig
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import org.yaml.snakeyaml.Yaml
import org.yaml.snakeyaml.constructor.Constructor
import java.util.ArrayList
import java.util.HashMap
import java.util.concurrent.TimeUnit

object Dependencies {
    @JvmStatic
    val yaml = Yaml(Constructor(AppConfig::class.java))

    @JvmStatic
    val cookieStore =
        HashMap<String, List<Cookie>>()


    @JvmStatic
    val gson = Gson()


    @JvmStatic
    val okhttp = OkHttpClient.Builder()
        .readTimeout(20, TimeUnit.SECONDS)
        .cookieJar(object : CookieJar {
            override fun saveFromResponse(httpUrl: HttpUrl, list: List<Cookie>) {
                cookieStore[httpUrl.host()] = list
                println("save $list")
            }

            override fun loadForRequest(httpUrl: HttpUrl): List<Cookie> {
                val cookies: List<Cookie>? = cookieStore[httpUrl.host()]
                println("load $cookies")
                return cookies ?: ArrayList()
            }
        }).addInterceptor {
            it.proceed(
                it.request().newBuilder().addHeader(
                    "User-Agent",
                    "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.83 Safari/537.36 Edg/81.0.416.41"
                ).build()
            )
        }.build()

}