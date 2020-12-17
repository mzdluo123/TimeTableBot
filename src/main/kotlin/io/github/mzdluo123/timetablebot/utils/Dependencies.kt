package io.github.mzdluo123.timetablebot.utils

import com.google.gson.Gson
import io.github.mzdluo123.timetablebot.config.AppConfig
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import org.yaml.snakeyaml.Yaml
import org.yaml.snakeyaml.constructor.Constructor
import java.net.InetSocketAddress
import java.net.Proxy
import java.net.SocketAddress
import java.time.LocalTime
import java.util.concurrent.TimeUnit

object Dependencies {
    @JvmStatic
    val yaml = Yaml(Constructor(AppConfig::class.java))

    @JvmStatic
    val gson = Gson()

    val classTimeTable by lazy {
        val list = mutableListOf<LocalTime>()
        AppConfig.getInstance().classTime.forEach {
            list.add(parseClassTime(it))
        }
        list
    }

}
