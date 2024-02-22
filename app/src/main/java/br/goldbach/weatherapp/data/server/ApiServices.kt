package br.goldbach.weatherapp.data.server

import br.goldbach.weatherapp.data.model.City
import br.goldbach.weatherapp.data.model.ForecastWeather
import br.goldbach.weatherapp.data.model.TodayWeather
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {

    @GET("current.json")
    suspend fun getCurrentWeather(
        @Query("key") apiKey: String,
        @Query("q") cityName: String,
        @Query("aqi") airQuality: String
    ) : Response<TodayWeather>


    @GET("forecast.json")
    suspend fun getForecastWeather(
        @Query("key") apiKey: String,
        @Query("q") cityName: String,
        @Query("days") days: String,
        @Query("aqi") airQuality: String,
        @Query("alerts") alerts: String
    ) : Response<ForecastWeather>

    @GET("search.json")
    suspend fun getCityDetails(
        @Query("key") apiKey: String,
        @Query("q") cityName: String
    ) : Response<City>


}