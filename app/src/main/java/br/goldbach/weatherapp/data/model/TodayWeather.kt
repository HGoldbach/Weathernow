package br.goldbach.weatherapp.data.model

import com.google.gson.annotations.SerializedName

data class TodayWeather(
    @SerializedName("current")
    val current: Current?,
    @SerializedName("location")
    val location: Location?
)