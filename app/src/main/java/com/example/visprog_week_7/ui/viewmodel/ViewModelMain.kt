package com.example.visprog_week_7.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.visprog_week_7.data.dto.WeatherResponse
import com.example.visprog_week_7.data.repository.WeatherRepo
import com.example.visprog_week_7.ui.model.WeatherModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ViewModelMain(
    private val repo: WeatherRepo = WeatherRepo()
): ViewModel() {
    private val _weatherRepo = MutableStateFlow<WeatherResponse?>(null)
    val weatherData: StateFlow<WeatherResponse?> = _weatherRepo

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _isError = MutableStateFlow<String?>("No Error")

    fun fetchWeather(city: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _isError.value = null
            try {
                val data = repo.getWeatherCity(city)
                _weatherRepo.value = data
            } catch (e: Exception) {
                _isError.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    //Turning DTO main data to model with WeatherModel as the data type
    fun WeatherResponse.toModel(): WeatherModel {
        return WeatherModel(
            cityName = this.name,
            countryCode = this.sys.countryCode,
            description = this.weather.firstOrNull()?.description ?: "No description",
            temperature = this.main.temp,
            humidity = this.main.humidity,
            windSpeed = this.wind.speed,
            cloudiness = this.clouds.all,
            sunrise = this.sys.sunrise,
            sunset = this.sys.sunset
        )
    }
}