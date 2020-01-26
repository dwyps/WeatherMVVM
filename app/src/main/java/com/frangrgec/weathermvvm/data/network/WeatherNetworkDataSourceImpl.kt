package com.frangrgec.weathermvvm.data.network

import android.util.Log
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.frangrgec.weathermvvm.data.network.response.CurrentWeatherResponse
import com.frangrgec.weathermvvm.internal.NoConnectivityException

class WeatherNetworkDataSourceImpl(
    private val apiService: WeatherApiInterface
) : WeatherNetworkDataSource {

    private val _downloadedCurrentWeather = MutableLiveData<CurrentWeatherResponse>()
    override val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>
        get() = _downloadedCurrentWeather

    override suspend fun fetchCurrentWeather(location: String) {

        try {
            val fetchedCurrentWeather = apiService.getCurrentWeather(location)
            _downloadedCurrentWeather.postValue(fetchedCurrentWeather)
        } catch (e: NoConnectivityException) {
            Log.e("Connectivity", "No Internet!", e)
        }

    }
}