package br.goldbach.weatherapp.data.repository

import br.goldbach.weatherapp.data.model.City
import br.goldbach.weatherapp.data.model.Condition
import br.goldbach.weatherapp.data.model.Current
import br.goldbach.weatherapp.data.model.Forecast
import br.goldbach.weatherapp.data.model.ForecastWeather
import br.goldbach.weatherapp.data.model.Location
import br.goldbach.weatherapp.data.model.TodayWeather
import br.goldbach.weatherapp.data.server.ApiServices
import br.goldbach.weatherapp.data.server.MockApiService
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test


class WeatherRepositoryTest {

   private lateinit var mockApi: ApiServices
   private lateinit var weatherRepository: WeatherRepository

   @Before
   fun setUp() {
       mockApi = MockApiService()
       weatherRepository = WeatherRepositoryImpl(mockApi)
   }

    @Test
    fun testGetCurrentWeather() = runBlocking {
        val cityName = "City"
        val airQuality = "no"
        val expectedResponse = TodayWeather(
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

        val result = weatherRepository.getCurrentWeather(cityName, airQuality)
        assertEquals(expectedResponse, result.body())
    }

    @Test
    fun testGetForecastWeather() = runBlocking {
        val cityName = "City"
        val days = 5
        val airQuality = "no"
        val alerts = "no"
        val expectedResponse = ForecastWeather(
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

        val result = weatherRepository.getForecastWeather(cityName, days, airQuality, alerts )
        assertEquals(expectedResponse, result.body())
    }

    @Test
    fun testGetCityDetails() = runBlocking {
        val city = City()
        val cityName = "Mafra"
        city.add(City.CityItem("Brazil", "Mafra"))
        val expectedResponse = city

        val result = weatherRepository.getCityDetails(cityName)
        assertEquals(expectedResponse, result.body())

    }


}