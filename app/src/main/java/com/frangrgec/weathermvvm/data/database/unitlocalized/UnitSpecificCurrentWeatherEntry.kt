package com.frangrgec.weathermvvm.data.database.unitlocalized

interface UnitSpecificCurrentWeatherEntry {

    val temperature: Double
    val weatherCode: Int
    val weatherIcons: List<String>
    val weatherDescriptions: List<String>
    val windSpeed: Double
    val windDirection: String
    val precipitationVolume: Double
    val feelsLikeTemperature: Double
    val visibilityDistance: Double

}