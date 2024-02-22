package br.goldbach.weatherapp.data.model

import com.google.gson.annotations.SerializedName

class City : ArrayList<City.CityItem>(){
    data class CityItem(
        @SerializedName("country")
        val country: String?,
        @SerializedName("id")
        val id: Int?,
        @SerializedName("lat")
        val lat: Double?,
        @SerializedName("lon")
        val lon: Double?,
        @SerializedName("name")
        val name: String?,
        @SerializedName("region")
        val region: String?,
        @SerializedName("url")
        val url: String?
    )
}