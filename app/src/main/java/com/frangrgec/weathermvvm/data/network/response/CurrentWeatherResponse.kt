package com.frangrgec.weathermvvm.data.network.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CurrentWeatherResponse(
    val request: Request,
    val location: Location,
    @Json(name = "current")
    val currentWeatherEntry: CurrentWeatherEntry
)