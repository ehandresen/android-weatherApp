package com.example.weatherapp.data

import com.example.weatherapp.network.WeatherApiService

// repository exposes the methods to get data from the network
interface WeatherRepository {
    suspend fun getCurrentWeather(endUrl: String): CurrentWeather
    suspend fun getForecastWeather(endUrl: String): ForecastWeather
}

// class to implement the interface
class WeatherRepositoryImpl : WeatherRepository {
    override suspend fun getCurrentWeather(endUrl: String): CurrentWeather {
        // this is the actual data source
        return WeatherApiService.WeatherApi.retrofitService.getCurrentWeather(endUrl)
    }

    override suspend fun getForecastWeather(endUrl: String): ForecastWeather {
        return WeatherApiService.WeatherApi.retrofitService.getForecastWeather(endUrl)
    }
}