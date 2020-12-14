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

    @JvmStatic
    private var cookies = hashMapOf<String, Cookie>()

    @JvmStatic
    val okhttp = getOkhttpClient()


    val classTimeTable by lazy {
        val list = mutableListOf<LocalTime>()
        AppConfig.getInstance().classTime.forEach {
            list.add(parseClassTime(it))
        }
        list
    }

    fun resetCookie() {
        cookies.clear()
    }

    fun getOkhttpClient(): OkHttpClient {

        var builder =  OkHttpClient.Builder()
            .readTimeout(20, TimeUnit.SECONDS)
            .cookieJar(object : CookieJar {

                override fun saveFromResponse(httpUrl: HttpUrl, list: List<Cookie>) {
                    for (cookie in list) {
                        cookies[cookie.name] = cookie
                    }
                }

                override fun loadForRequest(httpUrl: HttpUrl): List<Cookie> {
                    return cookies.values.toList()
                }
            })
            if (AppConfig.getInstance().proxy.enable){

                val address= InetSocketAddress(AppConfig.getInstance().proxy.address,AppConfig.getInstance().proxy.port)
                val proxy = Proxy(Proxy.Type.SOCKS,address)
                builder.proxy(proxy)
            }
//        .addInterceptor {
//            it.proceed(
//                it.request().newBuilder().addHeader(
//                    "User-Agent",
//                    "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.83 Safari/537.36 Edg/81.0.416.41"
//                ).build()
//            )
//        }
//        .addInterceptor(HttpLoggingInterceptor().apply {
//            level = HttpLoggingInterceptor.Level.BASIC
//        })
//        .addInterceptor { chain ->
//            chain.proceed(chain.request())
//        }
            return builder.build()

    }
}
