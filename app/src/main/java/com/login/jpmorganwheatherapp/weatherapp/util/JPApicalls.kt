package com.login.jpmorganwheatherapp.weatherapp.util

import com.login.jpmorganwheatherapp.weatherapp.data.CityLocationResponse
import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Query
/*Api interface with parameters*/
interface JPApicalls {
        @POST("weather?")
        fun getWeatherByCity(@Query("q") cityname : String,@Query("appid") apikey : String) : Call<CityLocationResponse>

    }