package br.goldbach.weatherapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.goldbach.weatherapp.data.model.City
import br.goldbach.weatherapp.data.model.TodayWeather
import br.goldbach.weatherapp.data.repository.WeatherRepository
import br.goldbach.weatherapp.data.repository.WeatherRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CityViewModel @Inject constructor(private val repository: WeatherRepository) : ViewModel() {

    private var cityLiveData = MutableLiveData<City>()
    private var citiesLiveData = MutableLiveData<TodayWeather>()

//    private var listOfCitiesToSearch: List<String> = listOf(
//        "Berlin",
//        "Lisbon",
//        "Stalingrad",
//        "Prague",
//        "Sao Paulo",
//        "Buenos Aires",
//        "Hong Kong"
//    )
//
//    private var cityList: MutableLiveData<TodayWeather> = MutableLiveData()

    fun fetchCity(cityName: String) {
        viewModelScope.launch {
            try {
                val response = repository.getCityDetails(cityName)
                val data: City = response.body()!!
                cityLiveData.postValue(data)
            } catch(e: Exception) {
                throw RuntimeException("Api call failed : ${e.message}", e)
            }
        }
    }

//    fun fetchTodayWeatherCities() {
//        viewModelScope.launch {
//            try {
//                listOfCitiesToSearch.map {
//                    val response = repository.getCurrentWeather(it, "no")
//                    cityList.add(response.body()!!)
//                }
//                citiesLiveData.postValue(cityList)
//            }
//        }
//    }

    fun observeCityLiveData(): LiveData<City> {
        return cityLiveData
    }

    fun observeCitiesLiveData() : LiveData<TodayWeather> {
        return citiesLiveData
    }
}