package com.login.jpmorganwheatherapp.weatherapp.util

import android.app.Activity
import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import java.util.*


class JPLocationLocationUtil {

    /*Check the internet*/
    fun checkForInternet(context: Context): Boolean {

        // register activity with the connectivity manager service
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        //Check build version M or higher
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            // Returns a Network object corresponding to
            // the currently active default data network.
            val network = connectivityManager.activeNetwork ?: return false

            // Representation of the capabilities of an active network.
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

            return when {
                // Indicates network uses a Wi-Fi transport orWiFi has network connectivity
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true

                // Indicates  network uses a Cellular transport. or Cellular has network connectivity
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true

                // else return false
                else -> false
            }
        } else {
            // if the android version is below M
            @Suppress("DEPRECATION") val networkInfo =
                connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }

    /*get City name basedon current location using Geocoder*/
    fun getCityName(activity: Activity, latitude:Double,  longitude:Double): String {

        val geocoder = Geocoder(activity, Locale.getDefault())
        //Gets the list of address based on lat long
        val addresses: List<Address>? = geocoder.getFromLocation(latitude, longitude, 1)
        //Gets the city name
        val cityName: String = addresses!![0].locality
        if(cityName!=null)
      return cityName
        else
            //if cityName is null sending empty value to search user from edittext
            return "";
    }


}
