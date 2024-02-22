package br.goldbach.weatherapp.data

import br.goldbach.weatherapp.data.repository.WeatherRepository
import br.goldbach.weatherapp.data.server.ApiClient
import br.goldbach.weatherapp.data.server.ApiServices
import br.goldbach.weatherapp.ui.viewmodel.CityViewModel
import br.goldbach.weatherapp.ui.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { CityViewModel(get()) }
    single<WeatherRepository> { WeatherRepository(get())}
    single { ApiClient().getClient().create(ApiServices::class.java) }

}