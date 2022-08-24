package io.applaunch.applaunchmini.repository.networks


import io.applaunch.applaunchmini.repository.networks.response.WeatherResponseModel
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query


interface RetrofitInterface {

    /** Login Starts */

    //Get User Data
    @Headers("Content-Type: application/json")
    @GET("data/2.5/onecall?")
    suspend fun getWeather(@Query("lat") lat:String,@Query("lon") lang:String,@Query("units") units:String,@Query("appid") appID:String): Response<WeatherResponseModel>


}