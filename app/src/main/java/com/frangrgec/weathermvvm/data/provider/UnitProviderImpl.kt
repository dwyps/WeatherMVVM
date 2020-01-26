package com.frangrgec.weathermvvm.data.provider

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.frangrgec.weathermvvm.internal.UnitSystem

class UnitProviderImpl(context: Context) : UnitProvider {

    private val appContext = context.applicationContext

    private val preferences: SharedPreferences
        get() = PreferenceManager.getDefaultSharedPreferences(appContext)

    override fun getUnitSystem(): UnitSystem {
        val selectedUnit = preferences.getString("UNIT_SYSTEM", UnitSystem.METRIC.name)
        return UnitSystem.valueOf(selectedUnit!!)
    }
}