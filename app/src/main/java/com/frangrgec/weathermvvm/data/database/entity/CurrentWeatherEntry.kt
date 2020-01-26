package com.frangrgec.weathermvvm.data.database.entity


import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

const val CURRENT_WEATHER_ID = 0

@Entity(tableName = "current_weather")
@JsonClass(generateAdapter = true)
data class CurrentWeatherEntry(

    /*@Json(name = "observation_time")
    val observationTime: String,
    @Json(name = "uv_index")
    val uvIndex: Int,*/
    val temperature: Double,
    @Json(name = "weather_code")
    val weatherCode: Int,
    @Json(name = "weather_icons")
    val weatherIcons: List<String>,
    @Json(name = "weather_descriptions")
    val weatherDescriptions: List<String>,
    @Json(name = "wind_speed")
    val windSpeed: Double,
    @Json(name = "wind_dir")
    val windDir: String,
    val precip: Double,
    val feelslike: Double,
    val visibility: Double,
    @Json(name = "is_day")
    val isDay: String
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = CURRENT_WEATHER_ID
}