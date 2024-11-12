package com.example.c2

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
    private const val BASE_URL_JSON_PLACEHOLDER = "https://jsonplaceholder.typicode.com/"

    private val retrofit =
        Retrofit.Builder().baseUrl(BASE_URL_JSON_PLACEHOLDER).addConverterFactory(GsonConverterFactory.create()).build()

    fun getInstance(): ApiServiceJsonPlaceholder {
        return retrofit.create(ApiServiceJsonPlaceholder::class.java)
    }
}