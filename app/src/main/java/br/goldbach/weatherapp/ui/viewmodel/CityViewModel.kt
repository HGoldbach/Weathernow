package br.goldbach.weatherapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.goldbach.weatherapp.data.model.City
import br.goldbach.weatherapp.data.repository.WeatherRepository
import kotlinx.coroutines.launch

class CityViewModel(private val repository: WeatherRepository) : ViewModel() {

    private var cityLiveData = MutableLiveData<City>()

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

    fun observeCityLiveData(): LiveData<City> {
        return cityLiveData
    }
}