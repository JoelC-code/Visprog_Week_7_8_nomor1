package com.example.visprog_week_7.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.visprog_week_7.data.dto.WeatherResponse
import com.example.visprog_week_7.data.repository.WeatherRepo
import com.example.visprog_week_7.ui.model.WeatherModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

class ViewModelMain(
    private val repo: WeatherRepo = WeatherRepo()
) : ViewModel() {

    private val _weatherModel = MutableStateFlow<WeatherModel?>(null)
    val weatherModel: StateFlow<WeatherModel?> = _weatherModel

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun fetchWeather(city: String) {
        viewModelScope.launch {println("Fetching weather for city: $city")
            _isLoading.value = true
            _errorMessage.value = null

            try {
                val data: WeatherResponse? = repo.getWeatherCity(city)
                println("WeatherResponse: $data")

                if (data == null) {
                    println("Data is null!")
                    _errorMessage.value = "No response from API"
                } else {
                    _weatherModel.value = data.toModel()
                    println("WeatherModel set successfully: ${_weatherModel.value}")
                }
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Unknown error"
                e.printStackTrace()
                println("Error caught in fetchWeather: ${e.message}")
            } finally {
                _isLoading.value = false
                println("Loading done")
            }
        }
    }

    //Turning DTO main data to model with WeatherModel as the data type
    fun WeatherResponse.toModel(): WeatherModel {
        //Formatting the time using java simple date format of hour:minutes
        val formatter = java.text.SimpleDateFormat("HH:mm", java.util.Locale.getDefault())
        formatter.timeZone = java.util.TimeZone.getDefault()

        //Formatting the unix as a string to be the following data formatting on top
        val sunriseFormat = formatter.format(java.util.Date(this.sys.sunrise * 1000L))
        val sunsetFormat = formatter.format(java.util.Date(this.sys.sunset * 1000L))

        return WeatherModel(
            cityName = this.name,
            countryCode = this.sys.country,
            description = this.weather.firstOrNull()?.description ?: "No description",
            temperature = (this.main.temp - 273.15).roundToInt(),
            humidity = this.main.humidity,
            windSpeed = this.wind.speed,
            cloudiness = this.clouds.all,
            sunrise = sunriseFormat,
            sunset = sunsetFormat,
            iconUrl = "https://openweathermap.org/img/wn/${this.weather.firstOrNull()?.icon ?: "01d"}@2x.png",
            rain = rain?.oneHour ?: 0.0,
            pressure = this.main.pressure,
        )
        println("Rain data: ${this.rain}")
    }
}