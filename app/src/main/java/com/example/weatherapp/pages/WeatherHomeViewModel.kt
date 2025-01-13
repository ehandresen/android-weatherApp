package com.example.weatherapp.pages

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.CurrentWeather
import com.example.weatherapp.data.ForecastWeather
import com.example.weatherapp.data.WeatherRepository
import com.example.weatherapp.data.WeatherRepositoryImpl
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class WeatherHomeViewModel : ViewModel() {
    private val weatherRepository: WeatherRepository = WeatherRepositoryImpl()
    var uiState: WeatherHomeUiState by mutableStateOf((WeatherHomeUiState.Loading))

    private val exceptionHandler = CoroutineExceptionHandler { _, _ ->
        uiState = WeatherHomeUiState.Error
    }

    // public function to call our private suspend functions
    // since they are suspend functions, they need to be called in a coroutine scope
    fun getWeatherData() {

        // we will return from this coroutine scope when both suspend functions are performed
        viewModelScope.launch(exceptionHandler) {
            uiState = try {
                val currentWeather = async { getCurrentData() }.await()
                val forecastWeather = async { getForecastData() }.await()

                // log to make sure the data is being fetched
                //Log.d("WeatherHomeViewModel", "Current weather: ${currentWeather.main!!.temp}")
                //Log.d("WeatherHomeViewModel", "Forecast weather: ${forecastWeather.list!!.size}")
                WeatherHomeUiState.Success(Weather(currentWeather, forecastWeather))
            } catch (e: Exception) {
                WeatherHomeUiState.Error
            }
        }
    }

    private suspend fun getCurrentData(): CurrentWeather {
        val endUrl = "weather?lat=59.911289&lon=10.671514&appid=981e92865e97d5bb3b8a7b8730a86f81&units=metric"
        return weatherRepository.getCurrentWeather(endUrl)
    }

    private suspend fun getForecastData(): ForecastWeather {
        val endUrl = "forecast?lat=59.911289&lon=10.671514&appid=981e92865e97d5bb3b8a7b8730a86f81&units=metric"
        return weatherRepository.getForecastWeather(endUrl)
    }
}