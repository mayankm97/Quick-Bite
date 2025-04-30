package com.example.quickbite.data

import com.example.quickbite.data.models.AddToCartRequest
import com.example.quickbite.data.models.AddToCartResponse
import com.example.quickbite.data.models.Address
import com.example.quickbite.data.models.AddressListResponse
import com.example.quickbite.data.models.AuthResponse
import com.example.quickbite.data.models.CartResponse
import com.example.quickbite.data.models.CategoriesResponse
import com.example.quickbite.data.models.ConfirmPaymentRequest
import com.example.quickbite.data.models.ConfirmPaymentResponse
import com.example.quickbite.data.models.FoodItemResponse
import com.example.quickbite.data.models.GenericMsgResponse
import com.example.quickbite.data.models.OAuthRequest
import com.example.quickbite.data.models.PaymentIntentRequest
import com.example.quickbite.data.models.PaymentIntentResponse
import com.example.quickbite.data.models.RestaurantsResponse
import com.example.quickbite.data.models.ReverseGeoCodeRequest
import com.example.quickbite.data.models.SignInRequest
import com.example.quickbite.data.models.SignUpRequest
import com.example.quickbite.data.models.UpdateCartItemRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface FoodApi {
    @GET("/categories")   // food is end point we are trying to fetch
    suspend fun getCategories(): Response<CategoriesResponse>

    @GET("/restaurants")
    suspend fun getRestaurants(@Query("lat") lat:Double, @Query("lon") lon:Double): Response<RestaurantsResponse>

    // we now need request, reponse model and end point
    @POST("/auth/signup")
    suspend fun signUp(@Body request: SignUpRequest): Response<AuthResponse>

    @POST("/auth/login")
    suspend fun signIn(@Body request: SignInRequest): Response<AuthResponse>

    @POST("/auth/oauth")
    suspend fun oAuth(@Body request: OAuthRequest): Response<AuthResponse>

    @GET("/restaurants/{restaurantId}/menu")
    suspend fun getFoodItemForRestaurant(@Path("restaurantId") restaurantId: String): Response<FoodItemResponse>

    @POST("/cart")
    suspend fun addToCart(@Body request: AddToCartRequest): Response<AddToCartResponse>

    @GET("/cart")
    suspend fun getCart(): Response<CartResponse>

    @PATCH("/cart")
    suspend fun updateCart(@Body request: UpdateCartItemRequest): Response<GenericMsgResponse>

    @DELETE("/cart/{cartItemId}")
    suspend fun deleteCartItem(@Path("cartItemId") cartItemId: String): Response<GenericMsgResponse>

    @GET("/addresses")
    suspend fun getUserAddress(): Response<AddressListResponse>

    @POST("/addresses/reverse-geocode")
    suspend fun reverseGeocode(@Body request: ReverseGeoCodeRequest): Response<Address>

    @POST("/addresses")
    suspend fun storeAddress(@Body address: Address): Response<GenericMsgResponse>

    @POST("/payments/create-intent")
    suspend fun getPaymentIntent(@Body request: PaymentIntentRequest): Response<PaymentIntentResponse>

    @POST("/payments/confirm/{paymentIntentId}")
    suspend fun verifyPurchase(
        @Body request: ConfirmPaymentRequest, @Path("paymentIntentId") paymentIntentId: String
    ): Response<ConfirmPaymentResponse>

}