package io.applaunch.applaunchmini.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import io.applaunch.applaunchmini.R
import io.applaunch.applaunchmini.repository.networks.RetrofitApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WeatherFragment : Fragment() {


    var temp: TextView? = null
    var windSpeed: TextView? = null
    var weather: TextView? = null
    var humidity: TextView? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?K
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_weather, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        CoroutineScope(Dispatchers.IO).launch {
            getTerritory()
        }
    }

    private suspend fun getTerritory() {

        val response = RetrofitApi.getServiceClass()!!.getWeather(
            "12.9082847623315",
            "77.65197822993314",
            "imperial",
            "b143bb707b2ee117e62649b358207d3e"
        )

        if (response.isSuccessful) {

            if (response.code() == 200) {
                val data = response.body()!!.currentWeather
                humidity!!.text = data!!.humidity.toString()
                temp!!.text = data.temp.toString()
                windSpeed!!.text = data.windSpeed.toString()
                weather!!.text = data.weather!![0].description.toString()
                println(response.body())
            }
        }
    }
}