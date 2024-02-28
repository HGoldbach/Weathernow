package br.goldbach.weatherapp.data.model

import com.google.gson.annotations.SerializedName

data class ForecastWeather(
    @SerializedName("current")
    val current: Current?,
    @SerializedName("forecast")
    val forecast: Forecast?,
    @SerializedName("location")
    val location: Location?
)