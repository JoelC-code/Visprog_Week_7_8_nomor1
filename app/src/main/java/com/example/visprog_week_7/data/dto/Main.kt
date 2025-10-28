package com.example.visprog_week_7.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class Main(
    val temp: Double,
    val humidity: Int,
    val feels_like: Double,
    val pressure: Int,
)