package br.goldbach.weatherapp.ui.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.goldbach.weatherapp.data.model.ForecastWeather
import br.goldbach.weatherapp.data.model.TodayWeather
import br.goldbach.weatherapp.data.repository.WeatherRepository
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class MainViewModel(private val repository: WeatherRepository) : ViewModel(){

    private var todayWeatherLiveData = MutableLiveData<TodayWeather>()
    private var forecastTodayLiveData = MutableLiveData<ForecastWeather>()
    private var forecastWeekLiveData = MutableLiveData<ForecastWeather>()

    fun loadCurrentWeather(cityName: String) {
        viewModelScope.launch {
            try {
                val response = repository.getCurrentWeather(cityName, "no")
                val data: TodayWeather = response.body()!!
                todayWeatherLiveData.postValue(data)
            } catch (e: Exception) {
                throw RuntimeException("Api call failed ${e.message}", e)
            }
        }
    }

    fun loadForecastWeather(cityName: String, days: Int) {
        viewModelScope.launch {
            try {
                val response = repository.getForecastWeather(cityName, days, "no", "no")
                val data: ForecastWeather = response.body()!!
                if(days == 1) {
                    forecastTodayLiveData.postValue(data)
                } else {
                    forecastWeekLiveData.postValue(data)
                }
            } catch (e: Exception) {
                throw RuntimeException("Api call failed ${e.message}", e)
            }
        }
    }

    fun observerTodayWeatherLiveData() : LiveData<TodayWeather> {
        return todayWeatherLiveData
    }

    fun observerForecastTodayLiveData() : LiveData<ForecastWeather> {
        return forecastTodayLiveData
    }

    fun observerForecastWeekLiveData() : LiveData<ForecastWeather> {
        return forecastWeekLiveData
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun convertDateToString(now: LocalDateTime): CharSequence {
        val dayName =
            now.dayOfWeek.name.lowercase().slice(IntRange(0, 2)).replaceFirstChar { it.uppercase() }
        val monthName = now.month.name.lowercase().replaceFirstChar { it.uppercase() }
        val day = now.dayOfMonth.toString()
        val time = "${now.hour}:${now.minute}"
        return "$dayName, $monthName $day $time"
    }
}

