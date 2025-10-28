package com.example.visprog_week_7.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class WeatherResponse(
    val name: String,
    val weather: List<Weather>,
    val main: Main,
    val wind: Wind,
    val clouds: Clouds,
    val sys: Sys,
    val rain: Rain ? = null
)
