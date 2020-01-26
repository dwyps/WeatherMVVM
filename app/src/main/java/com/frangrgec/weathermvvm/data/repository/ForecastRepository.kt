package com.frangrgec.weathermvvm.data.repository

import androidx.lifecycle.LiveData
import com.frangrgec.weathermvvm.data.database.unitlocalized.UnitSpecificCurrentWeatherEntry

interface ForecastRepository {

    suspend fun getCurrentWeather(metric: Boolean): LiveData<out UnitSpecificCurrentWeatherEntry>

}