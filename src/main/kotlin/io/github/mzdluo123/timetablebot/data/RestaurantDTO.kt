package io.github.mzdluo123.timetablebot.data


import com.google.gson.annotations.SerializedName

data class RestaurantDTO(
    val `data`: List<List<String>> = listOf(),
    @SerializedName("data_append")
    val dataAppend: List<Any> = listOf(),
    val legend: List<String> = listOf(),
    val legend2: List<Any> = listOf(),
    val success: Boolean = false,
    val xAxis: List<String> = listOf()
)