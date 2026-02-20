package com.example.openweatherapp.remote

import com.example.openweatherapp.model.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

// BASE: https://api.openweathermap.org/data/2.5/
// RELATIVE: weather?q=London,uk&APPID=fd5170925ce0dfda3da6fc6eb175b0a3

interface WeatherApi {

    @GET("weather")
    suspend fun searchCity(
        @Query("q") name: String,
        @Query("appid") apiKey: String
    ): WeatherResponse
}
