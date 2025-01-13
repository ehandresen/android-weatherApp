package com.example.weatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weatherapp.pages.WeatherHomeScreen
import com.example.weatherapp.pages.WeatherHomeViewModel
import com.example.weatherapp.ui.theme.WeatherAppTheme

//!! important to give internet permission in the manifest file

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeatherApp()
        }
    }
}

@Composable
fun WeatherApp(modifier: Modifier = Modifier) {
    val weatherHomeViewModel: WeatherHomeViewModel = viewModel()
    weatherHomeViewModel.getWeatherData()
    // ui will be created while the data is being fetched
    // when the data is fetched, we need to make sure our composable functions are recomposed with the weather data
    WeatherAppTheme {
        WeatherHomeScreen()
    }
}