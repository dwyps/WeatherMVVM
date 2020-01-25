package com.frangrgec.weathermvvm.data

import com.frangrgec.weathermvvm.data.network.response.CurrentWeatherResponse
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val API_KEY = "d445772b7d7f63171604f3fb87723d24"
const val BASE_URL = "http://api.weatherstack.com/"

//http://api.weatherstack.com/current?access_key=API_KEY&query=LOCATION&units=UNIT_M

interface WeatherApiInterface {

    @GET("current")
    suspend fun getCurrentWeather(
        @Query("access_key") apiKey: String =API_KEY,
        @Query("query") location: String,
        @Query("units") unit: String = "m"
    ): CurrentWeatherResponse


    companion object{

        fun getRetrofit() : WeatherApiInterface{

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()


            return retrofit.create(WeatherApiInterface::class.java)
        }


    }

}