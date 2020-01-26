package com.frangrgec.weathermvvm.data.database.unitlocalized

interface UnitSpecificCurrentWeatherEntry {

    val temperature: Double
    val weatherCode: Int
    //val weatherIcons: MutableList<String>
    //val weatherDescriptions: MutableList<String>
    val windSpeed: Double
    val windDirection: String
    val precipitationVolume: Double
    val feelsLikeTemperature: Double
    val visibilityDistance: Double

}