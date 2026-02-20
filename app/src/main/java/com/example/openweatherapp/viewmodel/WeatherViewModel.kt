package com.example.openweatherapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.openweatherapp.model.WeatherResponse
import com.example.openweatherapp.repository.WeatherRepository
import com.example.openweatherapp.states.WeatherResult
import kotlinx.coroutines.launch

class WeatherViewModel(private val repository: WeatherRepository = WeatherRepository()) : ViewModel() {
    private val _cityWeather = MutableLiveData<WeatherResult<WeatherResponse>>()
    val cityWeather: LiveData<WeatherResult<WeatherResponse>> = _cityWeather

    fun fetchWeather(cityName: String) {
        viewModelScope.launch {
            _cityWeather.value = WeatherResult.Loading
            _cityWeather.value = repository.getCityWeatherByName(cityName)
        }
    }
}
