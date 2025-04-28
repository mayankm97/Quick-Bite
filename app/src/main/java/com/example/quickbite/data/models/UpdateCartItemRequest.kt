package com.example.quickbite.data.models

data class UpdateCartItemRequest(
    val cartItemId: String,
    val quantity: Int
)
