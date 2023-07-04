package com.login.jpmorganwheatherapp

import com.login.jpmorganwheatherapp.weatherapp.data.Main.Companion.convertToFahrenheit
import com.login.jpmorganwheatherapp.weatherapp.data.Sys.Companion.convertTime
import org.junit.Assert
import org.junit.Test
import org.junit.Assert.*

class JPLocationResponseTest {
    @Test
    fun testConvertkelvinToFahrenheit() {
        val fahrenheit: String? = convertToFahrenheit(295.22)
        Assert.assertEquals("71.7", fahrenheit)
    }


    @Test(expected = ArithmeticException::class)
    fun testNotValidKelvin() {
        val fahrenheit: Double? = convertToFahrenheit(0.0).toDoubleOrNull()
        if (fahrenheit != null) {
            assertEquals("Check value", 0.0, fahrenheit, 0.0)
        }
    }


    @Test
    fun testConvertSunriseUnixToTime() {
        val time: String? = convertTime(1688388721)
        Assert.assertEquals("07:52:01 AM", time)
    }
    @Test(expected = ArithmeticException::class)
    fun testNotUnixToTime() {
        val fahrenheit: Double? = convertTime(10000000).toDoubleOrNull()
        if (fahrenheit != null) {
            assertEquals("Check value", 0.0, fahrenheit, 0.0)
        }
    }

}