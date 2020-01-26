package com.frangrgec.weathermvvm.data.network

import androidx.lifecycle.LiveData
import com.frangrgec.weathermvvm.data.network.response.CurrentWeatherResponse

interface WeatherNetworkDataSource {
    val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>

    suspend fun fetchCurrentWeather(
        location: String
    )
}