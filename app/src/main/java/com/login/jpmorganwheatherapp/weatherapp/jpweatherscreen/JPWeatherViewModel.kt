package com.login.jpmorganwheatherapp.weatherapp.jpweatherscreen


import android.annotation.SuppressLint
import android.location.Location
import android.location.LocationRequest
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener
import com.login.jpmorganwheatherapp.weatherapp.data.CityLocationResponse
import com.login.jpmorganwheatherapp.weatherapp.util.JPLocationLocationUtil
import com.login.jpmorganwheatherapp.weatherapp.util.JPconstant.Companion.API_KEY
import com.login.jpmorganwheatherapp.weatherapp.util.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class JPWeatherViewModel: ViewModel(){
    //variable that will listen to user's input from edittext
    var userInput = MutableLiveData<String>()
    //expose the variable to the owner(activity)
    val getUserInput: LiveData<String> = userInput
    lateinit var jpWeatherScreenActivity: JPWeatherScreenActivity;
    var getCityWeather = MutableLiveData<CityLocationResponse>()
    lateinit var  jpLocationLocationUtil: JPLocationLocationUtil
    private lateinit var fusedLocationClient: FusedLocationProviderClient




fun getContaxt(activity: JPWeatherScreenActivity, LocationUtil: JPLocationLocationUtil){
    //declarion of classes from Activity
    jpWeatherScreenActivity=activity
    jpLocationLocationUtil=LocationUtil
}



    fun getWeatherByCity(city:String) {
//Calling Api using RetrofitInstance. Api key i got from this website https://openweathermap.org/weather-conditions and city is user input
        RetrofitInstance.api.getWeatherByCity(city,API_KEY).enqueue(object  :
            Callback<CityLocationResponse> {
            override fun onResponse(call: Call<CityLocationResponse>, response: Response<CityLocationResponse>) {
                if (response.body()!=null){
                    //Stored response in livedata
                    getCityWeather.postValue(response.body())
                }
                else{
                    return
                }
            }
            override fun onFailure(call: Call<CityLocationResponse>, t: Throwable) {
            }
        })
    }



/*Click Search Button*/
    fun onSearch() {
    var description: String="";
    //check internet availblity
    if (jpLocationLocationUtil.checkForInternet(jpWeatherScreenActivity)) {
        //if internet is availble check edittext is not empty. if edittext is empty displaying toast like "Please enter city or state code or zipcode"
        if (getUserInput.value?.isNotEmpty() == true)
            //calling getWeatherByCity function
            getWeatherByCity(getUserInput.value!!);
        else
//            if edittext is empty displaying toast like "Please enter city or state code or zipcode"
            Toast.makeText(jpWeatherScreenActivity, "Please enter city or state code or zipcode", Toast.LENGTH_SHORT).show()

    }
    else{
//        if no internet displays this Toast
        Toast.makeText(jpWeatherScreenActivity, "Please check your internet", Toast.LENGTH_SHORT).show()
    }
}

    /*Get current location using getCurrentLocation, Its only for first app first launch or no weather searched  with location permission accepts*/

    @SuppressLint("MissingPermission", "SetTextI18n")
    fun getLocation(): String {

        var cityName:String="";
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(jpWeatherScreenActivity)
        fusedLocationClient.getCurrentLocation(LocationRequest.QUALITY_HIGH_ACCURACY, object : CancellationToken() {
            override fun onCanceledRequested(p0: OnTokenCanceledListener) = CancellationTokenSource().token

            override fun isCancellationRequested() = false
        })
            .addOnSuccessListener { location: Location? ->
                //get the current city on first launch
                if (location == null)
                else {
                    val lat = location.latitude
                    val lon = location.longitude
                    cityName=jpLocationLocationUtil.getCityName(jpWeatherScreenActivity,lat,lon)
                    getWeatherByCity( jpLocationLocationUtil.getCityName(jpWeatherScreenActivity,lat,lon))
                }

            }

return cityName;
    }
}


