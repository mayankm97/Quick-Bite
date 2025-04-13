package com.example.quickbite.data

import retrofit2.http.GET

interface FoodApi {
    @GET("/food")   // food is end point we are trying to fetch
    suspend fun getFood(): List<String>
}