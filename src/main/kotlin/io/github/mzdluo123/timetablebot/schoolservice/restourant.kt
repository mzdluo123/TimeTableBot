package io.github.mzdluo123.timetablebot.schoolservice

import io.github.mzdluo123.timetablebot.config.AppConfig
import io.github.mzdluo123.timetablebot.data.RestaurantDTO
import io.github.mzdluo123.timetablebot.utils.Dependencies
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.FormBody
import okhttp3.Request
import java.io.File

suspend fun getRestaurant(): RestaurantDTO {
    val rsp = withContext(Dispatchers.IO) {
        Dependencies.okhttp.newCall(
            Request.Builder().url(AppConfig.getInstance().restaurant.url)
                .post(FormBody.Builder()
                    .add("chartUuid", AppConfig.getInstance().restaurant.chartUuid)
                    .build()).build()
        ).execute()
    }
    return Dependencies.gson.fromJson(rsp.body!!.string(),RestaurantDTO::class.java)
}

suspend fun main() {
    AppConfig.loadConfig(File("config.yml") )
    val restaurant =  getRestaurant()

    val msg = buildString {
        for ( i in restaurant.xAxis.indices){
            append(restaurant.xAxis[i])
            append(" ")
            append(restaurant.data[0][i])
            append("\n")
        }
    }
    println(msg)
}