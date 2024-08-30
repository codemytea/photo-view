package com.example.photoview.api

import android.util.Log
import com.fasterxml.jackson.databind.json.JsonMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import retrofit2.create

object RetrofitClient {

    private val okhttp by lazy {
        OkHttpClient.Builder()
            .addInterceptor {
                val newUrl = it.request().url().newBuilder()
                    .addQueryParameter("api_key", "6bf889a9915b4ca0b77c2e4d11cd7c51")
                    .addQueryParameter("format", "json")
                    .build()
                val newRequest = it.request().newBuilder().url(newUrl).build()
                Log.d("Retrofit", newRequest.url().toString())
                val response = it.proceed(newRequest)
                val newBody = response.body()?.let{
                    ResponseBody.create(
                        it.contentType(),
                        it.string().removePrefix("jsonFlickrApi(").removeSuffix(")")
                    )
                }
                response.newBuilder().body(newBody).build();
            }
            .build()
    }

    private val retrofit by lazy {
        Retrofit
            .Builder()
            .baseUrl("https://www.flickr.com/")
            .client(okhttp)
            .addConverterFactory(
                JacksonConverterFactory.create(
                    JsonMapper.builder().addModules(JavaTimeModule()).build()
                )
            )
            .build()
    }

    //todo di?
    val flickrApi by lazy {
        retrofit.create<FlickrAPI>()
    }
}