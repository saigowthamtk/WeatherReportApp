package com.login.jpmorganwheatherapp

import com.google.android.material.textfield.TextInputEditText
import com.login.jpmorganwheatherapp.weatherapp.jpweatherscreen.JPWeatherScreenActivity
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.robolectric.Robolectric
import org.junit.Assert.assertTrue


class JPWeatherScreenTest {

    private var activity: JPWeatherScreenActivity? = null

    @Before
    fun setup() {
        activity = Robolectric.setupActivity(JPWeatherScreenActivity::class.java)
    }

    @Test
    @Ignore
    fun onSearch() {
        val editText: TextInputEditText = (activity?.binding?.editSearchLocation ?:"No edittext") as TextInputEditText
        assertTrue("Edittext does not contain text 'Lombard'", "Lombard".equals( editText.getText()))
    }
}