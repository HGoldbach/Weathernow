package br.goldbach.weatherapp.data.model

import com.google.gson.annotations.SerializedName

data class Day(
    @SerializedName("avghumidity")
    val avghumidity: Int?,
    @SerializedName("avgtemp_c")
    val avgtempC: Double?,
    @SerializedName("avgtemp_f")
    val avgtempF: Double?,

    @SerializedName("condition")
    val condition: Condition?,

    @SerializedName("maxtemp_c")
    val maxtempC: Double?,

    @SerializedName("mintemp_c")
    val mintempC: Double?,
)
