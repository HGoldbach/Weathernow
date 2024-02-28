package br.goldbach.weatherapp.data.server

import br.goldbach.weatherapp.data.model.City
import br.goldbach.weatherapp.data.model.Condition
import br.goldbach.weatherapp.data.model.Current
import br.goldbach.weatherapp.data.model.Forecast
import br.goldbach.weatherapp.data.model.ForecastWeather
import br.goldbach.weatherapp.data.model.Location
import br.goldbach.weatherapp.data.model.TodayWeather
import retrofit2.Response

class MockApiService : ApiServices {
    override suspend fun getCurrentWeather(
        apiKey: String,
        cityName: String,
        airQuality: String
    ): Response<TodayWeather> {
        val todayWeather = TodayWeather(
            current = Current(
                tempC = 25.0,
                feelslikeC = 22.0,
                condition = Condition(
                    code = null,
                    icon = null,
                    text = null
                )
            ),
            location = Location(
                name = "London",
                country = "UK"
            )
        )
        return Response.success(todayWeather)
    }

    override suspend fun getForecastWeather(
        apiKey: String,
        cityName: String,
        days: String,
        airQuality: String,
        alerts: String
    ): Response<ForecastWeather> {
        val forecastWeather = ForecastWeather(
            current = Current(
                tempC = 25.0,
                feelslikeC = 22.0,
                condition = Condition(
                    code = null,
                    icon = null,
                    text = null
                )
            ),
            forecast = Forecast(
                forecastday = null
            ),
            location = Location(
                name = "London",
                country = "UK"
            )
        )
        return Response.success(forecastWeather)
    }

    override suspend fun getCityDetails(
        apiKey: String,
        cityName: String
    ): Response<City> {
        val city = City()
        city.add(City.CityItem("Brazil", "Mafra"))
        return Response.success(city)
    }

}