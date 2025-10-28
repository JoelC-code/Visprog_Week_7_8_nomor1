package com.example.visprog_week_7.ui.model

data class WeatherModel(
    val cityName: String,
    val countryCode: String,
    val description: String,
    val temperature: Int,
    val humidity: Int,
    val windSpeed: Double,
    val cloudiness: Int,
    val sunrise: String,
    val sunset: String,
    val iconUrl: String,
    val rain: Double,
    val pressure: Int,
)