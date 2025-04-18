package com.example.quickbite.data

import com.example.quickbite.data.models.AuthResponse
import com.example.quickbite.data.models.SignUpRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface FoodApi {
    @GET("/food")   // food is end point we are trying to fetch
    suspend fun getFood(): List<String>

    // we now need request, reponse model and end point
    @POST("/auth/signup")
    suspend fun signUp(@Body request: SignUpRequest): AuthResponse
}