package com.example.m2k.weather


/**
 * Created by m2k on 10/7/17.
 */

class Image(private val s: String) {
    val image: Int
        get() {
            if (s == "clear-day") {
                return R.drawable.clear_day
            } else if (s == "clear-night") {
                return R.drawable.clear_night
            } else if (s == "cloudy")
                return R.drawable.cloudy
            else if (s == "fog")
                return R.drawable.fog
            else if (s == "patly-cloudy-day")
                return R.drawable.partly_cloudy_day
            else if (s == "partly-cloudy-night")
                return R.drawable.partly_cloudy_night
            else if (s == "rain")
                return R.drawable.rain
            else if (s == "sleet")
                return R.drawable.sleet
            else if (s == "snow")
                return R.drawable.snow
            else
                return R.drawable.wind
        }
}
