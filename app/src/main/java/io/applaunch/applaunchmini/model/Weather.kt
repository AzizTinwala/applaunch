package io.applaunch.applaunchmini.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




 class Weather {
     @SerializedName("id")
     @Expose
      val id: Int? = null

     @SerializedName("main")
     @Expose
      val main: String? = null

     @SerializedName("description")
     @Expose
      val description: String? = null

     @SerializedName("icon")
     @Expose
      val icon: String? = null
    override fun toString(): String {
        return "Weather(id=$id, main=$main, description=$description, icon=$icon)"
    }
}