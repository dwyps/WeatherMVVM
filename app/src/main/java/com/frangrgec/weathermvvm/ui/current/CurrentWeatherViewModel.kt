package com.frangrgec.weathermvvm.ui.current

import androidx.lifecycle.ViewModel
import com.frangrgec.weathermvvm.data.provider.UnitProvider
import com.frangrgec.weathermvvm.data.repository.ForecastRepository
import com.frangrgec.weathermvvm.internal.UnitSystem
import com.frangrgec.weathermvvm.internal.lazyDeferred

class CurrentWeatherViewModel(
    private val forecastRepository: ForecastRepository,
    unitProvider: UnitProvider
) : ViewModel() {

    private val unitSystem = unitProvider.getUnitSystem()

    val isMetric: Boolean
        get() = unitSystem == UnitSystem.METRIC

    val weather by lazyDeferred { forecastRepository.getCurrentWeather(isMetric) }
    val weatherLocation by lazyDeferred { forecastRepository.getWeatherLocation() }

}
