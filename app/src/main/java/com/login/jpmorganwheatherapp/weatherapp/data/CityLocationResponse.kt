package com.login.jpmorganwheatherapp.weatherapp.data

import java.text.SimpleDateFormat
import java.util.*

/*Data class for Api*/
data class CityLocationResponse(
    val visibility: Int? = null,
    val timezone: Int? = null,
    val main: Main? = null,
    val clouds: Clouds? = null,
    val sys: Sys? = null,
    val dt: Int? = null,
    val coord: Coord? = null,
    val weather: List<WeatherItem?>? = null,
    val name: String? = null,
    val cod: Int? = null,
    val id: Int? = null,
    val base: String? = null,
    val wind: Wind? = null
)

data class Main(
    val temp: Double? = null,
    val temp_min: Double? = null,
    val humidity: Int? = null,
    val pressure: Int? = null,
    val feels_like: Double? = null,
    val temp_max: Double? = null

) {

    companion object {
        fun convertToFahrenheit(temperature: Double): String {
			/*Default value to display is 0*/
            var fahrenheit = "0℉"
            /*Display Degree and fahrenheit symbal. Its helps for temperature details like current temperature, feels like, etc., */
            var fahrenheitsymbal="\u2109"
            if (temperature != null && !temperature.equals(0.0)) {
				/*Converting into Kelvin to fahrenheit*/
                val value: Double = ((((temperature - 273.15) * 9) / 5) + 32)
				/*Converting into one decimal like 72.16dp*/
                val oneDecimal: Double = String.format("%.1f", value).toDouble()
                fahrenheit = oneDecimal.toString()+fahrenheitsymbal
            }
            return fahrenheit;
        }


    }
}


data class Clouds(
    val all: Int? = null
)

data class Coord(
    val lon: Double? = null,
    val lat: Double? = null
)

data class Sys(
    val country: String? = null,
    val sunrise: Long? = null,
    val sunset: Long? = null,
    val id: Int? = null,
    val type: Int? = null
){
    companion object {
        fun convertTime(long: Long): String {

          //  val unixTimestamp converts to date, its helps for sunrise and sunset display
            val javaTimestamp = long * 1000L
            val date = Date(javaTimestamp)
            val sunriseorsunset: String = SimpleDateFormat("hh:mm:ss aa").format(date)
            return sunriseorsunset;
        }
    }
}

data class Wind(
    val deg: Int? = null,
    val speed: Double? = null
)

data class WeatherItem(
    val icon: String? = null,
    val description: String? = null,
    val main: String? = null,
    val id: Int? = null
)

