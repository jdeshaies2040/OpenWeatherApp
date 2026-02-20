package com.example.openweatherapp.repository

import com.example.openweatherapp.model.WeatherResponse
import com.example.openweatherapp.remote.RetrofitClient
import com.example.openweatherapp.states.WeatherResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WeatherRepository {

    suspend fun getCityWeatherByName(name: String) : WeatherResult<WeatherResponse> =
        withContext(Dispatchers.IO){
            try {
                val response = RetrofitClient.weatherApi.searchCity(name=name,apiKey="fd5170925ce0dfda3da6fc6eb175b0a3")
                WeatherResult.Success(response)

            } catch (e: Exception){
                WeatherResult.Error(e.message)
            }
        }
}
