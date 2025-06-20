package com.example.quickbite.data

import android.content.Context
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


// to setup retrofit along using hilt
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

//    provideClient(session: FoodHubSession): OkHttpClient
//    → Creates an OkHttpClient that automatically attaches
    //    your token (Bearer <token>) to every network request.

    //    provideRetrofit(client: OkHttpClient): Retrofit
//    → Builds a Retrofit instance using that client — so every API call uses the token.
//
//    provideFoodApi(retrofit: Retrofit): FoodApi
//    → Creates your API interface (FoodApi) to talk to backend.


    // In this whole functions, we modified the OkHttpClient to automatically attach the user's session token
    // (Authorization: Bearer <token>) with every backend call by using an Interceptor.
    @Provides
    fun provideClient(session: FoodHubSession): OkHttpClient {
        val client = OkHttpClient.Builder()
        client.addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer ${session.getToken()}")
                .build()
            chain.proceed(request)
        }
        client.addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        return client.build()
    }
    //OkHttpClient is the actual network engine that sends HTTP requests and
    // receives responses in your app.
    @Provides   // here we create a retrofit instance
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(client)
            .baseUrl("http://192.168.148.98:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    @Provides
    fun provideFoodApi(retrofit: Retrofit) : FoodApi {
        return retrofit.create(FoodApi::class.java)
    }

    @Provides
    fun provideSession(@ApplicationContext context: Context): FoodHubSession {
        return FoodHubSession(context)
    }

    @Provides
    fun providesLocationService(@ApplicationContext context: Context): FusedLocationProviderClient{
        return LocationServices.getFusedLocationProviderClient(context)
    }
}