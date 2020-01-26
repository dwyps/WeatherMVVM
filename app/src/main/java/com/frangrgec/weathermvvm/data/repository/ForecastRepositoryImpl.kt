package com.frangrgec.weathermvvm.data.repository

import androidx.lifecycle.LiveData
import com.frangrgec.weathermvvm.data.database.CurrentWeatherDao
import com.frangrgec.weathermvvm.data.database.WeatherLocationDao
import com.frangrgec.weathermvvm.data.database.entity.Location
import com.frangrgec.weathermvvm.data.database.unitlocalized.UnitSpecificCurrentWeatherEntry
import com.frangrgec.weathermvvm.data.network.WeatherNetworkDataSource
import com.frangrgec.weathermvvm.data.network.response.CurrentWeatherResponse
import com.frangrgec.weathermvvm.data.provider.LocationProvider
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.ZonedDateTime

class ForecastRepositoryImpl(
    private val currentWeatherDao: CurrentWeatherDao,
    private val weatherLocationDao: WeatherLocationDao,
    private val weatherNetworkDataSource: WeatherNetworkDataSource,
    private val locationProvider: LocationProvider
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

    override suspend fun getWeatherLocation(): LiveData<Location> {
        return withContext(IO) {
            return@withContext weatherLocationDao.getLocation()
        }
    }


    private fun persistFetchedCurrentWeather(fetchedWeather: CurrentWeatherResponse) {
        GlobalScope.launch(IO) {
            currentWeatherDao.upsert(fetchedWeather.currentWeatherEntry)
            weatherLocationDao.upsert(fetchedWeather.location)
        }
    }

    private suspend fun initWeatherData(metric: Boolean) {
        val lastWeatherLocation = weatherLocationDao.getLocation().value

        if (lastWeatherLocation == null
            || locationProvider.hasLocationChanged(lastWeatherLocation)
        ) {
            fetchCurrentWeather(metric)
            return
        }

        if (isFetchCurrentNeeded(lastWeatherLocation.zonedDateTime))
            fetchCurrentWeather(metric)
    }

    private suspend fun fetchCurrentWeather(metric: Boolean) {

        if (metric)
            weatherNetworkDataSource
                .fetchCurrentWeather(locationProvider.getPreferredLocationString(), "m")
        else
            weatherNetworkDataSource
                .fetchCurrentWeather(locationProvider.getPreferredLocationString(), "f")

    }

    private fun isFetchCurrentNeeded(lastFetchTime: ZonedDateTime): Boolean {
        val thirtyMinutesAgo = ZonedDateTime.now().minusMinutes(30)
        return lastFetchTime.isBefore(thirtyMinutesAgo)
    }
}