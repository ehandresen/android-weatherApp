package com.example.weatherapp.pages

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.weatherapp.R
import com.example.weatherapp.customUis.AppBackground
import com.example.weatherapp.data.CurrentWeather
import com.example.weatherapp.utils.getFormattedDate
import com.example.weatherapp.utils.getIconUrl

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherHomeScreen(
    uiState: WeatherHomeUiState,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        AppBackground(photoId = R.drawable.sky)
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("TopAppBar", style = MaterialTheme.typography.titleLarge) },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent,
                        actionIconContentColor = Color.White
                    )
                )
            },
            containerColor = Color.Transparent // by default scaffold background is white
        ) {
            Surface(
                color = Color.Transparent,
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
                    .wrapContentSize() // child content will be centered
            ) {
                when (uiState) {
                    is WeatherHomeUiState.Loading -> Text("Loading...")
                    is WeatherHomeUiState.Error -> Text("Error")
                    is WeatherHomeUiState.Success -> WeatherSection(weather = uiState.weather)
                }
            }
        }
    }
}

@Composable
fun WeatherSection(
    weather: Weather,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(8.dp)
    ) {
        CurrentWeatherSection(
            currentWeather = weather.currentWeather,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun CurrentWeatherSection(
    currentWeather: CurrentWeather,
    modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "${currentWeather.name}, ${currentWeather.sys?.country}",
            style = MaterialTheme.typography.titleMedium,
            color = Color.White
        )
        Text(
            text = getFormattedDate(currentWeather.dt!!),
            style = MaterialTheme.typography.titleMedium,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "${currentWeather.main?.temp?.toInt()}°C",
            style = MaterialTheme.typography.displayLarge,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Feels like ${currentWeather.main?.feelsLike?.toInt()}°C",
            style = MaterialTheme.typography.titleMedium,
            color = Color.White
        )
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(getIconUrl(currentWeather.weather?.get(0)?.icon!!))
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                modifier = Modifier.size((40.dp))
            )
            Text(
                text = currentWeather.weather?.get(0)?.description!!,
                style = MaterialTheme.typography.titleMedium,
                color = Color.White
            )
        }


    }
}