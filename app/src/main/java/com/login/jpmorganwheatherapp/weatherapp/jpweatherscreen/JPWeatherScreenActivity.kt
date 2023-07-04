package com.login.jpmorganwheatherapp.weatherapp.jpweatherscreen


import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.*
import com.login.jpmorganwheatherapp.R
import com.login.jpmorganwheatherapp.databinding.ActivityJpweatherScreenBinding
import com.login.jpmorganwheatherapp.weatherapp.data.CityLocationResponse
import com.login.jpmorganwheatherapp.weatherapp.util.JPLocationLocationUtil
import com.login.jpmorganwheatherapp.weatherapp.util.Shardpref
import java.util.*


class JPWeatherScreenActivity : AppCompatActivity() {
    lateinit var binding: ActivityJpweatherScreenBinding
    lateinit var weatherViewModel: JPWeatherViewModel
    lateinit var jpLocationLocationUtil: JPLocationLocationUtil
    lateinit var shardpref: Shardpref;
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /* Initalized the classes and viewmodel  */

        // Implemented view and obtain an instance of the binding class.
        binding = ActivityJpweatherScreenBinding.inflate(layoutInflater)
        // Implemented current activity as the lifecycle owner.
        binding.lifecycleOwner = this
        weatherViewModel = JPWeatherViewModel()
        shardpref = Shardpref(this)
        binding.jpWeatherViewModel = weatherViewModel
        binding.cityLocationresponse = CityLocationResponse()
        jpLocationLocationUtil = JPLocationLocationUtil()
        weatherViewModel.getContaxt(this, jpLocationLocationUtil);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        setContentView(binding.root)

        /* Location permission check  */
        checkPermissionRequired()

        //Obsered api from Live data, Using Retrofit get data and stored in live data- Retriving api validations in viewmodel class
        weatherViewModel.getCityWeather.observe(this, Observer<CityLocationResponse> { value ->
            binding.progressBar.setVisibility(View.GONE)
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            /*  Weather details are comes in array . so i tried to disply all the descriptions, so user can understand better weather report
              eg: first array description is Mist
              Second one is description is rainy . So i displayed like Mist / rainy*/
            if (value.weather != null && value.weather.size > 0) {
                shardpref.saveResponse(value);
                binding.weatherDescription.setText("")
                var description: String = "";
                        for (desc in value.weather.indices) {
                            if (desc == 0) {
                                description = value.weather?.get(desc)?.description.toString();
                                binding.weatherDescription.append(description)
                            } else
                                binding.weatherDescription.append("/" + description)
                }

                //Get the weather icon using Glide SDK
                getWeatherIcon(value.weather?.get(0)?.icon)
                hideSoftKeyboard(binding.editSearchLocation)
            } else {
                binding.weatherIcon.setImageResource(R.drawable.weather)
            }
        })

    }

    @SuppressLint("SuspiciousIndentation")
    fun dataCheckOnApplaunch(cityLocationResponse: CityLocationResponse?) {
        /* Auto-load the last city searched upon app launch. Data's are stored in shared pref  */
        if (cityLocationResponse != null) {
            weatherViewModel.getCityWeather.postValue(shardpref.getCityLocationResponse())
            /* set last searched city name in edittext */
            binding.editSearchLocation.setText(shardpref.getCityLocationResponse()!!.name)
        } else
            binding.progressBar.setVisibility(View.VISIBLE)
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        binding.editSearchLocation.setText(weatherViewModel.getLocation())
    }


    @SuppressLint("MissingPermission")
    fun checkPermissionRequired() {
        //Location Permission Checking
        val locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {

                    /* Permission granded and check shardpref isEmpty or null */
                    dataCheckOnApplaunch(shardpref.getCityLocationResponse());
                }
                permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {

                }
                else -> {
                }
            }
        }

        locationPermissionRequest.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }

    fun hideSoftKeyboard(view: View) {

        val imm =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun getWeatherIcon(icon: String?) {
        // This icon i got from api "icon": "04d" and i placed placeholder and error icon.
        val imageUrl: String =
            "https://openweathermap.org/img/wn/" + (icon
                ?: "+@+") + "@2x.png"

        val options: RequestOptions = RequestOptions()
            .centerCrop()
            .placeholder(R.drawable.weather)
            .error(R.drawable.url_failure)
        Glide.with(this).load(imageUrl).apply(options).into(binding.weatherIcon);
    }


}


