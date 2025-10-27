package com.example.visprog_week_7.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class Sys(
    val countryCode: String,
    val sunrise: Long,
    val sunset: Long
)