package br.goldbach.weatherapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.goldbach.weatherapp.data.model.City
import br.goldbach.weatherapp.data.model.TodayWeather
import br.goldbach.weatherapp.data.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CityViewModel @Inject constructor(private val repository: WeatherRepository) : ViewModel() {

    private var _cityLiveData = MutableLiveData<City>()
    val cityLiveData: LiveData<City> get() = _cityLiveData

    private var _citiesLiveData = MutableLiveData<List<TodayWeather>>()
    val citiesLiveData: LiveData<List<TodayWeather>> get() = _citiesLiveData
    private var _connectionError =  MutableLiveData(false)
    val connectionError: LiveData<Boolean> get() = _connectionError


    private var listOfCitiesToSearch: List<String> = listOf(
        "Berlin",
        "Lisbon",
        "Stalingrad",
        "Prague",
        "Sao Paulo",
        "Buenos Aires",
        "Hong Kong"
    )

    private var cityList: ArrayList<TodayWeather> = ArrayList()

    fun fetchCity(cityName: String) {
        viewModelScope.launch {
            try {
                val response = repository.getCityDetails(cityName)
                val data: City = response.body()!!
                _cityLiveData.postValue(data)
                _connectionError.value = false
            } catch(e: Exception) {
                _connectionError.value = true
             //   throw RuntimeException("Api call failed : ${e.message}", e)
            }
        }
    }

    fun fetchTodayWeatherCities() {
        viewModelScope.launch {
            try {
                listOfCitiesToSearch.map {
                    val response = repository.getCurrentWeather(it, "no")
                    cityList.add(response.body()!!)
                    _connectionError.value = false
                }
                _citiesLiveData.postValue(cityList)
            } catch (e: Exception) {
                _connectionError.value = true
            //    throw RuntimeException("Api call failed : ${e.message}", e)
            }
        }
    }

}