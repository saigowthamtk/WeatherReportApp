package com.login.jpmorganwheatherapp.weatherapp.util

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.google.gson.Gson
import com.login.jpmorganwheatherapp.weatherapp.data.CityLocationResponse

/*Shard pref to store values*/
class Shardpref (context: Context){
    private val PREFS_NAME = "sharedpref"
    val sharedPref: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
//Save the response
    fun saveResponse(cityLocationResponse: CityLocationResponse) {
    //response is key and its converted by Gson
        sharedPref.edit().putString("response", Gson().toJson(cityLocationResponse)).apply()
    }

    //get the response
    fun getCityLocationResponse(): CityLocationResponse? {
        //response is key
        val data = sharedPref.getString("response", null)
        if (data == null) {
            return null
        }
        return Gson().fromJson(data, CityLocationResponse::class.java)
    }
}