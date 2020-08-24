package io.github.mzdluo123.timetablebot.utils

import io.github.mzdluo123.timetablebot.config.AppConfig
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import org.yaml.snakeyaml.Yaml
import org.yaml.snakeyaml.constructor.Constructor
import java.util.*

object Dependencies {
    @JvmStatic
    val yaml = Yaml(Constructor(AppConfig::class.java))

    @JvmStatic
    val cookieStore =
        HashMap<String, List<Cookie>>()

    @JvmStatic
    val okhttp = OkHttpClient.Builder().cookieJar(object : CookieJar {
        override fun saveFromResponse(httpUrl: HttpUrl, list: List<Cookie>) {
            cookieStore[httpUrl.host()] = list
        }

        override fun loadForRequest(httpUrl: HttpUrl): List<Cookie> {
            val cookies: List<Cookie>? = cookieStore[httpUrl.host()]
            return cookies ?: ArrayList()
        }
    }).build()

}