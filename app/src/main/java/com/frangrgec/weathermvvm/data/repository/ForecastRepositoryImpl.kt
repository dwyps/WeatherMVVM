package com.frangrgec.weathermvvm.data.repository

import androidx.lifecycle.LiveData
import com.frangrgec.weathermvvm.data.database.CurrentWeatherDao
import com.frangrgec.weathermvvm.data.database.unitlocalized.UnitSpecificCurrentWeatherEntry
import com.frangrgec.weathermvvm.data.network.WeatherNetworkDataSource
import com.frangrgec.weathermvvm.data.network.response.CurrentWeatherResponse
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.ZonedDateTime

class ForecastRepositoryImpl(
    private val currentWeatherDao: CurrentWeatherDao,
    private val weatherNetworkDataSource: WeatherNetworkDataSource
) : ForecastRepository {

    init {
        weatherNetworkDataSource.downloadedCurrentWeather.observeForever { newCurrentWeather ->
            persistFetchedCurrentWeather(newCurrentWeather)
        }
    }

    override suspend fun getCurrentWeather(metric: Boolean): LiveData<out UnitSpecificCurrentWeatherEntry> {
        return withContext(IO) {
            initWeatherData(metric)
            return@withContext currentWeatherDao.getWeatherMetric()
        }
    }

    private fun persistFetchedCurrentWeather(fetchedWeather: CurrentWeatherResponse) {
        GlobalScope.launch(IO) {
            currentWeatherDao.upsert(fetchedWeather.currentWeatherEntry)
        }
    }

    private suspend fun initWeatherData(metric: Boolean) {
        if (isFetchCurrentNeeded(ZonedDateTime.now().minusHours(1)))
            fetchCurrentWeather(metric)
    }

    private suspend fun fetchCurrentWeather(metric: Boolean) {
        if (metric) weatherNetworkDataSource.fetchCurrentWeather("Osijek", "m")
        else weatherNetworkDataSource.fetchCurrentWeather("Osijek", "f")
    }

    private fun isFetchCurrentNeeded(lastFetchTime: ZonedDateTime): Boolean {
        val thirtyMinutesAgo = ZonedDateTime.now().minusMinutes(30)
        return lastFetchTime.isBefore(thirtyMinutesAgo)
    }
}