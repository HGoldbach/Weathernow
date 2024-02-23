package br.goldbach.weatherapp.data.repository

import br.goldbach.weatherapp.BuildConfig
import br.goldbach.weatherapp.data.model.City
import br.goldbach.weatherapp.data.model.ForecastWeather
import br.goldbach.weatherapp.data.model.TodayWeather
import br.goldbach.weatherapp.data.server.ApiServices
import retrofit2.Response
import javax.inject.Inject


class WeatherRepositoryImpl @Inject constructor(private val api: ApiServices) : WeatherRepository {

    override suspend fun getCurrentWeather(
        cityName: String,
        airQuality: String
    ): Response<TodayWeather> {
        return api.getCurrentWeather(BuildConfig.API_KEY, cityName, airQuality)
    }

    override suspend fun getForecastWeather(
        cityName: String, days: Int, airQuality: String, alerts: String
    ): Response<ForecastWeather> {
        return api.getForecastWeather(
            BuildConfig.API_KEY,
            cityName,
            days.toString(),
            airQuality,
            alerts
        )
    }

    override suspend fun getCityDetails(cityName: String): Response<City> {
        return api.getCityDetails(BuildConfig.API_KEY, cityName)
    }

}