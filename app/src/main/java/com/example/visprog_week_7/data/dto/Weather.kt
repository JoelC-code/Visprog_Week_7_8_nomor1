package com.example.visprog_week_7.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class Weather(
    val description: String,
    val icon: String
)