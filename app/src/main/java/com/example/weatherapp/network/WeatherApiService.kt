package com.example.weatherapp.network

import com.example.weatherapp.data.CurrentWeather
import com.example.weatherapp.data.ForecastWeather
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Url

// This file holds logic for establishing a network connection
private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

// create methods for each endpoint in the api
interface WeatherApiService {

    // 'suspend' function will let us use coroutines, and not block the main thread (the ui), fetches asynchronously
    // retrofit will combine the base url and the end url to get the data and return a CurrentWeather object
    // retrofit will also convert the json data to a CurrentWeather object
    @GET()
    suspend fun getCurrentWeather(@Url endUrl: String): CurrentWeather

    @GET()
    suspend fun getForecastWeather(@Url endUrl: String): ForecastWeather

    object WeatherApi {

        // lazy initialization means that the object will only be initialized when it is first used. It can save memory significantly
        val retrofitService: WeatherApiService by lazy {
            retrofit.create(WeatherApiService::class.java)
        }
    }
}
