package io.applaunch.applaunchmini.repository.networks

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.applaunch.applaunchmini.utils.Constants.Companion.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class RetrofitApi {

    companion object {
        private fun getRetrofit(url: String): Retrofit? {
            val gson: Gson = GsonBuilder().setLenient().create()
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            val okHttpClient: OkHttpClient.Builder = OkHttpClient.Builder()
            okHttpClient.connectTimeout(120, TimeUnit.SECONDS)
            okHttpClient.readTimeout(120, TimeUnit.SECONDS)
            okHttpClient.addInterceptor(logging)
            return Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        private var retrofit: RetrofitInterface? = null

        fun  getServiceClass(): RetrofitInterface? {
            return if (retrofit == null) {
                retrofit = getRetrofit(BASE_URL)!!.create(RetrofitInterface::class.java)
                retrofit
            } else {
                retrofit
            }
        }

    }
}