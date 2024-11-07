package com.example.c2

import com.google.gson.annotations.SerializedName
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("/api/9757ed57650d4cdba6fcf83fd5c60730/search/{name}")
    suspend fun getSuperheroes(@Path("name") superheroName:String): Response<SuperHeroDataResponse>
}

data class SuperHeroDataResponse(
    @SerializedName("response") val response:String,
    @SerializedName("results-for") val resultsFor:String,
    @SerializedName("results") val results:List<SuperHeroItemResponse>

)

data class SuperHeroItemResponse(
    @SerializedName("id") val id:String,
    @SerializedName("name") val name:String
)