package com.example.visprog_week_7

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.visprog_week_7.ui.theme.Visprog_Week_7Theme
import com.example.visprog_week_7.ui.view.WeatherView

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Visprog_Week_7Theme {
                WeatherView()
            }
        }
    }
}