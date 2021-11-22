package com.example.weather

import com.example.weather.model.Model
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {
    //    @GET("weather?q=Minsk&APPID=6e298e72d16587b721abb30bbf7c721a&units=metric&lang=ru") //full link
    @GET("weather")
    fun weather(
        @Query("q") q: String,
        @Query("APPID") APPID: String,
        @Query("units") units: String,
        @Query("lang") lang: String
    ): Call<Model>


}