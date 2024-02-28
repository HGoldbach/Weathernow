package br.goldbach.weatherapp.data.model

import com.google.gson.annotations.SerializedName

data class Location(
    @SerializedName("country")
    val country: String?,
    @SerializedName("name")
    val name: String?,
)