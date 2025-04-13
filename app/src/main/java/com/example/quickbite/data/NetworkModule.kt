package com.example.quickbite.data

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


// to setup retrofit along using hilt
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides   // here we create a retrofit instance
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://127.0.0.1:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    @Provides
    fun provideFoodApi(retrofit: Retrofit) : FoodApi {
        return retrofit.create(FoodApi::class.java)
    }
}