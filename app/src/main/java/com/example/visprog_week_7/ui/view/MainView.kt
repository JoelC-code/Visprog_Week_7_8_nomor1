package com.example.visprog_week_7.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Warning
import coil.compose.AsyncImage
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.visprog_week_7.ui.viewmodel.ViewModelMain
import com.example.visprog_week_7.R
import com.example.visprog_week_7.ui.model.WeatherModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun WeatherView(viewModelWeather: ViewModelMain = viewModel()) {
    var cityInput by rememberSaveable { mutableStateOf("") }
    val isLoading by viewModelWeather.isLoading.collectAsState()
    val weatherData by viewModelWeather.weatherModel.collectAsState()
    val errorMessage by viewModelWeather.errorMessage.collectAsState()
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = "Background Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
        )
        Column(
            modifier = Modifier
                .statusBarsPadding()
                .padding(horizontal = 20.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedTextField(
                    value = cityInput,
                    placeholder = { Text(text = "Enter City", color = Color.White) },
                    onValueChange = { cityInput = it },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.White,
                        focusedLabelColor = Color.White,
                        unfocusedLabelColor = Color.White,
                        focusedContainerColor = Color(0x20FFFFFF),
                        unfocusedContainerColor = Color(0x20FFFFFF)
                    ),
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .height(60.dp),
                    shape = RoundedCornerShape(12.dp)
                )
                Button(
                    onClick = {
                        if (cityInput.isNotBlank()) {
                            viewModelWeather.fetchWeather(cityInput.trim())
                        }
                    },
                    enabled = !isLoading,
                    contentPadding = PaddingValues(12.dp),
                    modifier = Modifier
                        .height(60.dp)
                        .border(1.dp, Color.White, RoundedCornerShape(12.dp)),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Color.White,
                        containerColor = Color(0x20FFFFFF),
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon",
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Search",
                        color = Color.White
                    )
                }
            }

            when {
                weatherData != null -> WeatherContent(weatherData)
                errorMessage != null -> ErrorScreen(errorMessage)
                isLoading -> LoadingScreen()
                else -> StartingScreen()
            }
        }
    }
}

@Composable
fun LoadingScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Loading...",
            color = Color(0xFFFFFFFF),
            fontSize = 18.sp
        )
    }
}

@Composable
fun StartingScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = "Search Icon",
            tint = Color(0x46FFFFFF),
            modifier = Modifier
                .size(70.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Search a city to get started",
            color = Color(0x46FFFFFF),
            fontSize = 15.sp
        )
    }
}

@Composable
fun ErrorScreen(message: String?) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.Warning,
            contentDescription = "Warning Icon",
            tint = Color(0xFFFF0000),
            modifier = Modifier
                .size(70.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Oops! Something went wrong.",
            color = Color(0xFFFFFFFF),
            fontSize = 18.sp
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = message ?: "Unknown error occurred.",
            color = Color(0x46FFFFFF),
            fontSize = 15.sp
        )
    }
}

@Composable
fun WeatherContent(weather: WeatherModel?) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Row(
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Place,
                    contentDescription = "Place Icon",
                    tint = Color.White,
                    modifier = Modifier.size(30.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "${weather?.cityName}", color = Color.White, fontSize = 27.sp)
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = todayDate(),
                fontSize = 42.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }

        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 44.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AsyncImage(
                        model = weather?.iconUrl,
                        contentDescription = "Weather Icon",
                        modifier = Modifier.size(100.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "${weather?.description}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        color = Color.White
                    )
                    Text(
                        text = "${weather?.temperature}°C",
                        fontWeight = FontWeight.Bold,
                        fontSize = 40.sp,
                        color = Color.White
                    )
                }
                Image(
                    painter = if (weather?.description?.contains(
                            "rain",
                            ignoreCase = true
                        ) == true
                    ) {
                        painterResource(id = R.drawable.panda_rain)
                    } else if (weather?.description?.contains("cloud", ignoreCase = true) == true) {
                        painterResource(id = R.drawable.panda_cloud)
                    } else {
                        painterResource(id = R.drawable.panda_hot)
                    },
                    contentDescription = "Logo Weather",
                    modifier = Modifier
                        .size(200.dp)
                )
            }
        }

        item {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(15.dp)
                ) {
                    WidgetItem(
                        painterResource(id = R.drawable.icon_humidity),
                        "HUMIDITY",
                        "${weather?.humidity}%"
                    )
                    WidgetItem(
                        painterResource(id = R.drawable.icon_wind),
                        "WIND",
                        "${weather?.windSpeed}km/h"
                    )
                    WidgetItem(
                        painterResource(id = R.drawable.temp),
                        "FEELS LIKE",
                        "${weather?.temperature}°"
                    )
                }
                Spacer(modifier = Modifier.height(15.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(15.dp)
                ) {
                    WidgetItem(
                        painterResource(id = R.drawable.rain),
                        "RAIN FALL",
                        "${weather?.rain} mm"
                    )
                    WidgetItem(
                        painterResource(id = R.drawable.devices),
                        "PRESSURE",
                        "${weather?.pressure}hPa"
                    )
                    WidgetItem(
                        painterResource(id = R.drawable.cloud),
                        "CLOUDS",
                        "${weather?.cloudiness}%"
                    )
                }
                Spacer(modifier = Modifier.height(32.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    WidgetItem(
                        painterResource(id = R.drawable.sunrise),
                        "SUNRISE",
                        "${weather?.sunrise} am"
                    )
                    WidgetItem(
                        painterResource(id = R.drawable.sunset),
                        "SUNSET",
                        "${weather?.sunset} PM"
                    )
                }
            }
        }
    }
}

@Composable
fun WidgetItem(icon: Painter ,title: String, value: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .background(
                color = Color(0x0FFFFFFF),
                shape = RoundedCornerShape(8.dp)
            )
            .width(100.dp)
            .height(100.dp)
    ) {
        Image(
            painter = icon,
            contentDescription = "Widget Icon",
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = title,
            fontSize = 12.sp,
            color = Color(0x54FFFFFF)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = value,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}

fun todayDate(): String {
    val today = Date()
    val formatter = SimpleDateFormat("d MMMM", Locale.getDefault())
    return formatter.format(today)
}


@Composable
@Preview(showBackground = true, showSystemUi = true)
fun WeatherPreview() {
    WeatherView()
}