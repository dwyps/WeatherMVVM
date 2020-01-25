package com.frangrgec.weathermvvm.ui.current

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.viewModelScope

import com.frangrgec.weathermvvm.R
import com.frangrgec.weathermvvm.data.WeatherApiInterface
import com.squareup.moshi.Moshi
import kotlinx.android.synthetic.main.current_weather_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

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

        val weatherApiInterface = WeatherApiInterface.getRetrofit()


        viewModel.viewModelScope.launch {
            val response = weatherApiInterface.getCurrentWeather(location = "London",unit = "m").currentWeatherEntry.temperature
            Log.i("radi",response.toString())
            withContext(Main) {
                textView.text=response.toString()
            }
            }
    }

}
