package com.frangrgec.weathermvvm.data.provider

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.frangrgec.weathermvvm.data.database.entity.Location
import com.frangrgec.weathermvvm.internal.LocationPermissionNotGrantedException
import com.frangrgec.weathermvvm.internal.asDeferred
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.Deferred

const val USE_DEVICE_LOCATION = "USE_DEVICE_LOCATION"
const val CUSTOM_LOCATION = "CUSTOM_LOCATION"

class LocationProviderImpl(
    private val fusedLocationProviderClient: FusedLocationProviderClient,
    context: Context
) : PreferenceProvider(context), LocationProvider {

    private val appContext = context.applicationContext

    override suspend fun hasLocationChanged(lastWeatherLocation: Location): Boolean {

        val deviceLocationChanged = try {

            hasDeviceLocationChanged(lastWeatherLocation)

        } catch (e: LocationPermissionNotGrantedException) {

            false
        }


        return deviceLocationChanged || hasCostumeLocationChanged(lastWeatherLocation)
    }

    override suspend fun getPreferredLocationString(): String {
        if (isUsingDeviceLocation()) {
            try {

                val deviceLocation =
                    getDeviceLastLocation().await() ?: return "${getCustomeLocationName()}"
                return "${deviceLocation.latitude},${deviceLocation.longitude}"

            } catch (e: LocationPermissionNotGrantedException) {

                return "${getCustomeLocationName()}"

            }
        } else {

            return "${getCustomeLocationName()}"
        }
    }

    private suspend fun hasDeviceLocationChanged(lastWeatherLocation: Location): Boolean {
        if (!isUsingDeviceLocation())
            return false

        val deviceLocation = getDeviceLastLocation().await() ?: return false

        val comparisonThreshold = 0.03

        return Math.abs(deviceLocation.latitude - lastWeatherLocation.lat) > comparisonThreshold &&
                Math.abs(deviceLocation.longitude - lastWeatherLocation.lon) > comparisonThreshold

    }

    private fun hasCostumeLocationChanged(lastWeatherLocation: Location): Boolean {
        val customLocationName = getCustomeLocationName()
        return customLocationName != lastWeatherLocation.name
    }

    private fun isUsingDeviceLocation(): Boolean {
        return preferences.getBoolean(USE_DEVICE_LOCATION, true)
    }

    private fun getCustomeLocationName(): String? {
        return preferences.getString(CUSTOM_LOCATION, null)
    }

    private fun getDeviceLastLocation(): Deferred<android.location.Location?> {
        return if (hasLocationPermission())
            fusedLocationProviderClient.lastLocation.asDeferred()
        else
            throw LocationPermissionNotGrantedException()
    }

    private fun hasLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            appContext,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }
}