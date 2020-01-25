package com.frangrgec.weathermvvm.data.network.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Location(
    val name: String,
    val country: String,
    val region: String,
    val lat: String,
    val lon: String,
    @Json(name = "timezone_id")
    val timezoneId: String,
    val localtime: String,
    @Json(name = "localtime_epoch")
    val localtimeEpoch: Int,
    @Json(name = "utc_offset")
    val utcOffset: String
)