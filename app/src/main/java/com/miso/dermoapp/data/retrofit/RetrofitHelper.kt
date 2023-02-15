package com.miso.dermoapp.data.retrofit

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
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

        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val httpClientBuilder = OkHttpClient.Builder().addInterceptor(logging)

        httpClientBuilder.addInterceptor { chain ->
            val original: Request = chain.request()
            val requestBuild: Request.Builder = original
                .newBuilder()
                .addHeader("dermo-traceability-id", UUID.randomUUID().toString())
                .method(original.method, original.body)
            val request: Request = requestBuild.build()
                chain.proceed(request)
        }
        httpClientBuilder.connectTimeout(60, TimeUnit.SECONDS)
        httpClientBuilder.readTimeout(60, TimeUnit.SECONDS)
        httpClientBuilder.writeTimeout(60, TimeUnit.SECONDS)

        return Retrofit.Builder()
            .baseUrl("http://dermoappmovil-env.eba-vqrznmv9.us-east-1.elasticbeanstalk.com/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(httpClientBuilder.build())
            .build()
    }
}