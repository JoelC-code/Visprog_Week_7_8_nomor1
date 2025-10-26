package com.example.visprog_week_7.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.visprog_week_7.data.dto.WeatherResponse
import com.example.visprog_week_7.data.repository.WeatherRepo
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

    private val _isError = MutableStateFlow<String?>(null)
    val isError: StateFlow<String?> = _isError

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
}