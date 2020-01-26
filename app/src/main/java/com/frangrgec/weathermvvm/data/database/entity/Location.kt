package com.frangrgec.weathermvvm.data.database.entity


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import org.threeten.bp.Instant
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime

const val WEATHER_LOCATION_ID = 0

@Entity(tableName = "weather_location")
@JsonClass(generateAdapter = true)
data class Location(
    val name: String,
    val country: String,
    val region: String,
    val lat: Double,
    val lon: Double,
    @Json(name = "timezone_id")
    val timezoneId: String,
    @Json(name = "localtime_epoch")
    val localtimeEpoch: Long,
    @Json(name = "utc_offset")
    val utcOffset: String
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = WEATHER_LOCATION_ID

    val zonedDateTime: ZonedDateTime
        get() {
            val instant = Instant.ofEpochSecond(localtimeEpoch)
            val zoneId = ZoneId.of(timezoneId)
            return ZonedDateTime.ofInstant(instant, zoneId)
        }

}