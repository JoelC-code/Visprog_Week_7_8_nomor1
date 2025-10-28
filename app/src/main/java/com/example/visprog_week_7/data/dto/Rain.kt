package com.example.visprog_week_7.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Rain(
    @SerialName("1h") val oneHour: Double? = null,
)