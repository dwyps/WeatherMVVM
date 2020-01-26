package com.frangrgec.weathermvvm.data.database.unitlocalized

import androidx.room.ColumnInfo

data class MetricCurrentWeatherEntry(
    override val temperature: Double,
    override val weatherCode: Int,
    //override val weatherIcons: MutableList<String>,
    //override val weatherDescriptions: MutableList<String>,
    override val windSpeed: Double,
    @ColumnInfo(name = "windDir")
    override val windDirection: String,
    @ColumnInfo(name = "precip")
    override val precipitationVolume: Double,
    @ColumnInfo(name = "feelslike")
    override val feelsLikeTemperature: Double,
    @ColumnInfo(name = "visibility")
    override val visibilityDistance: Double
) : UnitSpecificCurrentWeatherEntry