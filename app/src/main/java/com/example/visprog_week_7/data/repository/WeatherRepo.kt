package com.example.visprog_week_7.data.repository

import com.example.visprog_week_7.data.dto.WeatherResponse
import com.example.visprog_week_7.data.service.WeatherService

class WeatherRepo(
    private val service: WeatherService = WeatherService
) {
    suspend fun getWeatherCity(city: String): WeatherResponse {
        //take data from WeatherService with the data type of WeatherResponse
        //fetch using the city name
        val data = service.getWeatherUrl(cityName = city)
        //return the data
        return data
    }
}