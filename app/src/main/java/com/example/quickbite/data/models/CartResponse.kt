package com.example.quickbite.data.models

data class CartResponse(
    val checkoutDetails: CheckoutDetails,
    val items: List<CartItem>
)