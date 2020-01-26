package com.frangrgec.weathermvvm.ui.current

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope

import com.frangrgec.weathermvvm.R
import com.frangrgec.weathermvvm.data.network.ConnectivityInterceptorImpl
import com.frangrgec.weathermvvm.data.network.WeatherApiInterface
import com.frangrgec.weathermvvm.data.network.WeatherNetworkDataSource
import com.frangrgec.weathermvvm.data.network.WeatherNetworkDataSourceImpl
import kotlinx.android.synthetic.main.current_weather_fragment.*
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CurrentWeatherFragment : Fragment() {

    companion object {
        fun newInstance() = CurrentWeatherFragment()
    }

    private lateinit var viewModel: CrrentWeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.current_weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CrrentWeatherViewModel::class.java)


        //TODO experimental part goes to repository
        val weatherApiInterface = WeatherApiInterface(ConnectivityInterceptorImpl(this.context!!))
        val weatherNetworkDataSource = WeatherNetworkDataSourceImpl(weatherApiInterface)

        weatherNetworkDataSource.downloadedCurrentWeather.observe(this, Observer { response ->
            Log.i("radi",response.toString())
            textView.text = response.toString()
        })

        viewModel.viewModelScope.launch {
            weatherNetworkDataSource.fetchCurrentWeather("London")
        }
    }

}
