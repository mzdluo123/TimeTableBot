package io.github.mzdluo123.timetablebot.utils

import io.github.mzdluo123.timetablebot.config.AppConfig
import okhttp3.*
import java.io.Closeable
import java.net.InetSocketAddress
import java.net.Proxy
import java.util.concurrent.TimeUnit

class TTBHttpClient(val useProxy:Boolean = false) : Closeable {
    val okhttp: OkHttpClient
    private val cookies = hashMapOf<String, Cookie>()

    init {
        val builder = OkHttpClient.Builder()
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
        if (useProxy && AppConfig.getInstance().proxy.enable) {

            val address = InetSocketAddress(AppConfig.getInstance().proxy.address, AppConfig.getInstance().proxy.port)
            val proxy = Proxy(Proxy.Type.SOCKS, address)
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
        okhttp = builder.build()


    }

    override fun close() {
        okhttp.dispatcher.executorService.shutdown()
        okhttp.cache?.close()
        okhttp.connectionPool.evictAll()
    }

    public fun resetCookie(){
        cookies.clear()
    }
    public fun newCall(request: Request) = okhttp.newCall(request)

}