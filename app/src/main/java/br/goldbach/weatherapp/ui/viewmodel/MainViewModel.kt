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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: WeatherRepository) : ViewModel(){

    private var _todayWeatherLiveData = MutableLiveData<TodayWeather>()
    val todayWeatherLiveData: LiveData<TodayWeather> get() = _todayWeatherLiveData

    private var _forecastTodayLiveData = MutableLiveData<ForecastWeather>()
    val forecastTodayLiveData: LiveData<ForecastWeather> get() = _forecastTodayLiveData

    private var _forecastWeekLiveData = MutableLiveData<ForecastWeather>()
    val forecastWeekLiveData: LiveData<ForecastWeather> get() = _forecastWeekLiveData

    fun loadCurrentWeather(cityName: String) {
        viewModelScope.launch {
            try {
                val response = repository.getCurrentWeather(cityName, "no")
                val data: TodayWeather = response.body()!!
                _todayWeatherLiveData.postValue(data)
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
                    _forecastTodayLiveData.postValue(data)
                } else {
                    _forecastWeekLiveData.postValue(data)
                }
            } catch (e: Exception) {
                throw RuntimeException("Api call failed ${e.message}", e)
            }
        }
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

