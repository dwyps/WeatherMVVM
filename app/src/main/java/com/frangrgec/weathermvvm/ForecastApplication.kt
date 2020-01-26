package com.frangrgec.weathermvvm

import android.app.Application
import androidx.preference.Preference
import androidx.preference.PreferenceManager
import com.frangrgec.weathermvvm.data.database.ForecastDatabase
import com.frangrgec.weathermvvm.data.network.*
import com.frangrgec.weathermvvm.data.provider.UnitProvider
import com.frangrgec.weathermvvm.data.provider.UnitProviderImpl
import com.frangrgec.weathermvvm.data.repository.ForecastRepository
import com.frangrgec.weathermvvm.data.repository.ForecastRepositoryImpl
import com.frangrgec.weathermvvm.ui.current.CurrentWeatherViewModelFactory
import com.jakewharton.threetenabp.AndroidThreeTen
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class ForecastApplication : Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@ForecastApplication))

        bind() from singleton { ForecastDatabase(instance()) }
        bind() from singleton { instance<ForecastDatabase>().currentWeatherDao() }
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind() from singleton { WeatherApiInterface(instance()) }
        bind<WeatherNetworkDataSource>() with singleton { WeatherNetworkDataSourceImpl(instance()) }
        bind<ForecastRepository>() with singleton { ForecastRepositoryImpl(instance(), instance()) }
        bind<UnitProvider>() with singleton { UnitProviderImpl(instance()) }
        bind() from provider { CurrentWeatherViewModelFactory(instance(), instance()) }


    }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false)
    }
}