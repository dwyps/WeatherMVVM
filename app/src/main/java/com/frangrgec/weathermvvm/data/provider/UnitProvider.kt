package com.frangrgec.weathermvvm.data.provider

import com.frangrgec.weathermvvm.internal.UnitSystem

interface UnitProvider {

    fun getUnitSystem(): UnitSystem

}