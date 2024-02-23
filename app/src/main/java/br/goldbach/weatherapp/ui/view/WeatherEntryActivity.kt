package br.goldbach.weatherapp.ui.view

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import br.goldbach.weatherapp.data.model.City
import br.goldbach.weatherapp.data.model.TodayWeather
import br.goldbach.weatherapp.databinding.ActivityWeatherEntryBinding
import br.goldbach.weatherapp.ui.adapter.CitiesAdapter
import br.goldbach.weatherapp.ui.adapter.CityAdapter
import br.goldbach.weatherapp.ui.viewmodel.CityViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WeatherEntryActivity : AppCompatActivity() {

    @Inject lateinit var cityAdapter: CityAdapter
    @Inject lateinit var citiesAdapter: CitiesAdapter
    private lateinit var binding: ActivityWeatherEntryBinding
    private val cityViewModel: CityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeatherEntryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.apply {
            addFlags(
                WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS
            )
            statusBarColor = Color.TRANSPARENT
        }

        getCity()
        observerCity()
        loadCities()
        observerCities()
        setRecycler()

    }

    private fun observerCities() {
        cityViewModel.observeCitiesLiveData().observe(this@WeatherEntryActivity) {
            setAdapterCities(it)
        }
    }

    private fun setAdapterCities(it: List<TodayWeather>) {
        citiesAdapter.update(it)
    }

    private fun loadCities() {
        cityViewModel.fetchTodayWeatherCities()
    }

    private fun setRecycler() {
       binding.view3.apply {
           layoutManager = LinearLayoutManager(
               this@WeatherEntryActivity,
               LinearLayoutManager.VERTICAL,
               false
           )
           adapter = cityAdapter
       }
        binding.view4.apply {
            layoutManager = LinearLayoutManager(
                this@WeatherEntryActivity,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            adapter = citiesAdapter
        }
    }

    private fun observerCity() {
        cityViewModel.observeCityLiveData().observe(this@WeatherEntryActivity) {
            setAdapter(it)
        }
    }

    private fun setAdapter(city: City) {
        cityAdapter.updateData(city)
    }

    private fun getCity() {
         binding.cityEdt.addTextChangedListener(object : TextWatcher {
             override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
             override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

             override fun afterTextChanged(p0: Editable?) {
                 cityViewModel.fetchCity(p0.toString())
             }

         })
    }
}

