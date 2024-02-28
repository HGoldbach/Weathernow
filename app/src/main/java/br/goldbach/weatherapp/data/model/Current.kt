package br.goldbach.weatherapp.data.model

import com.google.gson.annotations.SerializedName

data class Current(
    @SerializedName("condition")
    val condition: Condition?,
    @SerializedName("feelslike_c")
    val feelslikeC: Double?,
    @SerializedName("temp_c")
    val tempC: Double?
)
