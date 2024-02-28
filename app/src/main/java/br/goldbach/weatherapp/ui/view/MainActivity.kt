package br.goldbach.weatherapp.ui.view

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import br.goldbach.weatherapp.R
import br.goldbach.weatherapp.data.model.ForecastWeather
import br.goldbach.weatherapp.data.model.TodayWeather
import br.goldbach.weatherapp.databinding.ActivityMainBinding
import br.goldbach.weatherapp.ui.adapter.ForecastTodayAdapter
import br.goldbach.weatherapp.ui.adapter.ForecastWeekAdapter
import br.goldbach.weatherapp.ui.viewmodel.MainViewModel
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime
import javax.inject.Inject
import kotlin.math.roundToInt

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()
    @Inject lateinit var forecastTodayAdapter: ForecastTodayAdapter
    @Inject lateinit var forecastWeekAdapter: ForecastWeekAdapter

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        window.apply {
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            statusBarColor = Color.TRANSPARENT
        }

        loadCity()
        observerTodayWeather()
        loadTodayForecast()
        loadWeekForecast()
        observerTodayForecast()
        observerWeekForecast()
        onBackClicked()
    }

    private fun onBackClicked() {
        binding.backImg.setOnClickListener {
            val intent = Intent(this@MainActivity, WeatherEntryActivity::class.java)
            startActivity(intent)
        }
    }

    private fun observerWeekForecast() {
        mainViewModel.forecastWeekLiveData.observe(this) {
            setWeekForecastAdapter(it)
        }
    }

    private fun setWeekForecastAdapter(it: ForecastWeather) {
        val days = it.forecast?.forecastday?.map { it?.day }
        val dates = it.forecast?.forecastday?.map { it?.date }
        forecastWeekAdapter.updateData(days, dates)
        setUpWeekRecycler()
    }

    private fun setUpWeekRecycler() {
        binding.view2.apply {
            layoutManager = LinearLayoutManager(
                this@MainActivity,
                LinearLayoutManager.VERTICAL,
                false
            )
            adapter = forecastWeekAdapter
        }
    }

    private fun observerTodayForecast() {
        mainViewModel.forecastTodayLiveData.observe(this@MainActivity) {
            setTodayForecastAdapter(it)
        }
    }

    private fun setTodayForecastAdapter(it: ForecastWeather) {
        val hours = it.forecast?.forecastday?.flatMap { it?.hour!! }
        forecastTodayAdapter.update(hours)
        setUpTodayRecycler()
    }

    private fun setUpTodayRecycler() {
        binding.view1.apply {
            layoutManager = LinearLayoutManager(
                this@MainActivity,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            adapter = forecastTodayAdapter
        }
    }

    private fun loadTodayForecast() {
        val name = intent.getStringExtra("name")
        mainViewModel.loadForecastWeather(name!!, 1)
    }

    private fun loadWeekForecast() {
        val name = intent.getStringExtra("name")
        mainViewModel.loadForecastWeather(name!!, 7)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun observerTodayWeather() {
        mainViewModel.todayWeatherLiveData.observe(this@MainActivity) {
            runOnUiThread {
                setTodayWeatherUi(it)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    private fun setTodayWeatherUi(it: TodayWeather) {
        binding.cityNameTxt.text = it.location?.name
        binding.dateTxt.text = mainViewModel.convertDateToString(LocalDateTime.now())
        binding.tempTxt.text = "${it.current?.tempC?.roundToInt()}°"
        binding.statusNowTxt.text = it.current?.condition?.text
        binding.thermalSensationTxt.text = "Feels like ${it.current?.feelslikeC?.roundToInt()}°"

        Picasso
            .get()
            .load("https:${it.current?.condition?.icon}")
            .placeholder(R.drawable.loading_img)
            .error(R.drawable.ic_broken_image)
            .into(binding.img1)
    }

    private fun loadCity() {
        val name = intent.getStringExtra("name")
        mainViewModel.loadCurrentWeather(name!!)
    }

}