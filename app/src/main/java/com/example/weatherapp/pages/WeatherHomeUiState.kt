package com.example.weatherapp.pages

import com.example.weatherapp.data.CurrentWeather
import com.example.weatherapp.data.ForecastWeather

// this file will hold all the data classes, all the information shown on the screen
data class Weather(
    val currentWeather: CurrentWeather,
    val forecastWeather: ForecastWeather
)

// sealed means you can only implement this interface in the same file
sealed interface WeatherHomeUiState {
    data class Success(val weather: Weather) : WeatherHomeUiState
    data object Error : WeatherHomeUiState
    data object Loading : WeatherHomeUiState

}