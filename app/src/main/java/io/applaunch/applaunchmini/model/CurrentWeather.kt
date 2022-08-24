package io.applaunch.applaunchmini.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class CurrentWeather {

    @SerializedName("temp")
    @Expose
    val temp: Double? = null


    @SerializedName("humidity")
    @Expose
     val humidity: Int? = null


    @SerializedName("wind_speed")
    @Expose
     val windSpeed: Double? = null


    @SerializedName("weather")
    @Expose
     val weather: List<Weather>? = null

}