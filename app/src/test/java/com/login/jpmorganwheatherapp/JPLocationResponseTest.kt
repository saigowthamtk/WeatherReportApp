package com.login.jpmorganwheatherapp

import android.app.Activity
import com.login.jpmorganwheatherapp.weatherapp.data.CityLocationResponse
import com.login.jpmorganwheatherapp.weatherapp.data.Main.Companion.convertToFahrenheit
import com.login.jpmorganwheatherapp.weatherapp.data.Sys.Companion.convertTime
import org.junit.Assert
import org.junit.Test

class JPLocationResponseTest {
    @Test
    fun testConvertkelvinToFahrenheit() {
        val fahrenheit: String? = convertToFahrenheit(295.22)
        Assert.assertEquals("71.7", fahrenheit)
    }

    @Test
    fun testConvertSunriseUnixToTime() {
        val time: String? = convertTime(1688388721)
        Assert.assertEquals("07:52:01 AM", time)
    }
}