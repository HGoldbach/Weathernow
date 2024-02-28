package br.goldbach.weatherapp.data.model

import com.google.gson.annotations.SerializedName

class City : ArrayList<City.CityItem>(){
    data class CityItem(
        @SerializedName("country")
        val country: String?,
        @SerializedName("name")
        val name: String?,
    )
}