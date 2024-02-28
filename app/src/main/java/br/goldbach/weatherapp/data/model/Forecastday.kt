package br.goldbach.weatherapp.data.model

import com.google.gson.annotations.SerializedName

data class Forecastday(
    @SerializedName("date")
    val date: String?,
    @SerializedName("day")
    val day: Day?,
    @SerializedName("hour")
    val hour: List<Hour?>?
)
