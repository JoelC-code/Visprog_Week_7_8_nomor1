package com.example.visprog_week_7.data.service

import com.example.visprog_week_7.data.dto.WeatherResponse
import io.ktor.client.*
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.*

object WeatherService {
    //For API key from openweather and the base link for it
    private const val API_KEY = "98635c26076df75bb38cb374c0f3bceb"
    private const val BASE_URL = "https://api.openweathermap.org/data/2.5/weather"

    //in here, we call the browser to connect to my app
    private val client = HttpClient(Android) {
        //we install it with content negotiation plugin
        install(ContentNegotiation) {
            //result would be json file
            json()
        }
    }

    //suspend function because we waited for the user to press the search button
    suspend fun getWeatherUrl(cityName: String): WeatherResponse {
        //connect the string as a url but string, adding the city name, base url and my api key
        //the following will be what the url will looks like (mock)
        //val url = URL("https://api.openweathermap.org/data/2.5/weather?q=Jakarta&appid=API_KEY")
        val urlString = "$BASE_URL?q=$cityName&appid=$API_KEY"
        //make a response to the client to show the url string
        val response: HttpResponse = client.get(urlString)
        //return the response as string text to be shown in the view
        return response.body<WeatherResponse>()
    }
}