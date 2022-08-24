package io.applaunch.applaunchmini.repository.networks.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.applaunch.applaunchmini.model.CurrentWeather

class WeatherResponseModel {

    @SerializedName("current")
    @Expose
    var currentWeather: CurrentWeather? = null
    override fun toString(): String {
        return "WeatherResponseModel(currentWeather=$currentWeather)"
    }
}