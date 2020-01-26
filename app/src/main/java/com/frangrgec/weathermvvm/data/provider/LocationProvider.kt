package com.frangrgec.weathermvvm.data.provider

import com.frangrgec.weathermvvm.data.database.WeatherLocationDao
import com.frangrgec.weathermvvm.data.database.entity.Location

interface LocationProvider {

    suspend fun hasLocationChanged(lastWeatherLocation: Location): Boolean
    suspend fun getPreferredLocationString(): String

}