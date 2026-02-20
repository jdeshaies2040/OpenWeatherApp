package com.example.openweatherapp

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.openweatherapp.databinding.ActivityMainBinding
import com.example.openweatherapp.states.WeatherResult
import com.example.openweatherapp.viewmodel.WeatherViewModel
import com.google.android.material.progressindicator.CircularProgressIndicator

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: WeatherViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpViewModel()
        observeData()

        viewModel.fetchWeather("London")

        binding.btnSubmit.setOnClickListener {
            val query = binding.etTargetCity.text.toString().trim()
            viewModel.fetchWeather(query)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setUpViewModel() {
        viewModel = ViewModelProvider(this)[WeatherViewModel::class.java]
    }

    private fun observeData() {
        viewModel.cityWeather.observe(this) { result ->
            when(result) {
                is WeatherResult.Loading -> CircularProgressIndicator(this)
                is WeatherResult.Error -> Toast.makeText(this, result.message, Toast.LENGTH_LONG).show()
                is WeatherResult.Success -> {
                    val weatherData = result.data
                    Log.i("Target City Weather", weatherData.toString())
                    binding.tvCityName.text = "City: ${weatherData.name}"
                    binding.tvWeatherDescription.text = "Description: ${weatherData.weather[0].description}"
                    binding.tvWeatherTemperature.text = "Temperature: ${weatherData.main.temp}"

                    Glide.with(binding.ivWeatherImage.context)
                        .load("https://openweathermap.org/payload/api/media/file/${weatherData.weather[0].icon}.png")
                        .placeholder(R.mipmap.ic_launcher)
                        .circleCrop()
                        .into(binding.ivWeatherImage)
                }
            }
        }
    }
}