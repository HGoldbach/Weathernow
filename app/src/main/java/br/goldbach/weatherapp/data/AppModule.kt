package br.goldbach.weatherapp.data

import br.goldbach.weatherapp.data.model.City
import br.goldbach.weatherapp.data.model.ForecastWeather
import br.goldbach.weatherapp.data.model.TodayWeather
import br.goldbach.weatherapp.data.repository.WeatherRepository
import br.goldbach.weatherapp.data.repository.WeatherRepositoryImpl
import br.goldbach.weatherapp.data.server.ApiClient
import br.goldbach.weatherapp.data.server.ApiServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Singleton
    @Provides
    fun provideWeatherRepository(api: ApiServices) : WeatherRepository {
        return WeatherRepositoryImpl(api)
    }

    @Singleton
    @Provides
    fun provideApiServices() : ApiServices {
        return ApiClient().getClient().create(ApiServices::class.java)
    }

    @Provides
    fun provideCityList() : List<City.CityItem> {
        return ArrayList()
    }

    @Provides
    fun provideForecastDayHours() : List<ForecastWeather.Forecast.Forecastday.Hour> {
        return ArrayList()
    }

    @Provides
    fun provideForecastDays() : List<ForecastWeather.Forecast.Forecastday.Day> {
        return ArrayList()
    }

    @Provides
    fun provideForecastDates() :  List<String> {
        return ArrayList()
    }

    @Provides
    fun provideCities() : List<TodayWeather> {
        return ArrayList()
    }


}