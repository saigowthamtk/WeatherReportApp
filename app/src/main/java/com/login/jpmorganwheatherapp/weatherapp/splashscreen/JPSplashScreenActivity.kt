package com.login.jpmorganwheatherapp.weatherapp.splashscreen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.login.jpmorganwheatherapp.R
import com.login.jpmorganwheatherapp.weatherapp.jpweatherscreen.JPWeatherScreenActivity
import kotlinx.coroutines.*

class JPSplashScreenActivity : AppCompatActivity() {
    val activityScope = CoroutineScope(Dispatchers.Main)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jpsplash_screen)

       /* Using coroutines to launch app */
        activityScope.launch {
            delay(3000)
            var intent = Intent(this@JPSplashScreenActivity, JPWeatherScreenActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onPause() {
        activityScope.cancel()
        super.onPause()
    }


}