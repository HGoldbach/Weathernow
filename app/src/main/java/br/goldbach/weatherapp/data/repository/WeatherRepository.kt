package br.goldbach.weatherapp.data.repository

import br.goldbach.weatherapp.data.model.City
import br.goldbach.weatherapp.data.model.ForecastWeather
import br.goldbach.weatherapp.data.model.TodayWeather
import retrofit2.Response

interface WeatherRepository {
    suspend fun getCurrentWeather(cityName: String, airQuality: String) : Response<TodayWeather>
    suspend fun getForecastWeather(cityName: String, days: Int, airQuality: String, alerts: String
    ): Response<ForecastWeather>
    suspend fun getCityDetails(cityName: String) : Response<City>
}