package com.example.quickbite.data.models

data class AddToCartRequest(
    val restaurantId: String,
    val menuItemId: String,
    val quantity: Int
)
