package com.frangrgec.weathermvvm.data.network

import android.util.Log
import com.frangrgec.weathermvvm.data.network.response.CurrentWeatherResponse
import okhttp3.OkHttpClient
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
        @Query("query") location: String,
        @Query("units") unit: String = "m"
    ): CurrentWeatherResponse


    companion object{

        operator fun invoke(

            connectivityInterceptor: ConnectivityInterceptor

        ): WeatherApiInterface {

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor { chain ->

                    //Get the network request and get the URL
                    val request = chain.request()
                    val httpUrl = request.url()

                    //Append the API key to the new URL
                    val newHttpUrl = httpUrl.newBuilder().addQueryParameter(
                        "access_key",
                        API_KEY
                    ).build()
                    val newRequest = request.newBuilder().url(newHttpUrl).build()

                    //Check URL
                    Log.i("radi", newHttpUrl.toString())

                    chain.proceed(newRequest)
                }
                .addInterceptor(connectivityInterceptor)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
                .create(WeatherApiInterface::class.java)
        }


    }

}