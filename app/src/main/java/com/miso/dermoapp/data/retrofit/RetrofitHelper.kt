package com.miso.dermoapp.data.retrofit

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/****
 * Project: DermoApp
 * From: com.miso.dermoapp.data.retrofit
 * Created by Jhonnatan E. Zamudio P. on 29/01/2023 at 1:33 p. m.
 * All rights reserved 2023.
 ****/

object RetrofitHelper {
    fun getRetrofit(): Retrofit {
        val gson = GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create()

        val okHttpClient: OkHttpClient = OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .baseUrl("http://dermoapp.us-east-1.elasticbeanstalk.com/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
    }
}