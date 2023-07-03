package com.login.jpmorganwheatherapp.weatherapp.util

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
        val api : JPApicalls by lazy {
            Retrofit.Builder()
                .baseUrl(JPconstant.API)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(JPApicalls::class.java)
        }

    }
