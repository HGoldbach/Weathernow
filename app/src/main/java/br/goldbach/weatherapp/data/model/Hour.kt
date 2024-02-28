package br.goldbach.weatherapp.data.model

import com.google.gson.annotations.SerializedName

data class Hour(
    @SerializedName("condition")
    val condition: Condition?,

    @SerializedName("feelslike_c")
    val feelslikeC: Double?,

    @SerializedName("humidity")
    val humidity: Int?,

    @SerializedName("temp_c")
    val tempC: Double?,

    @SerializedName("time")
    val time: String?,
)
