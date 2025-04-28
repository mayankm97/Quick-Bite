package com.example.quickbite.data.models

data class CheckoutDetails(
    val deliveryFee: Double,
    val subTotal: Double,
    val tax: Double,
    val totalAmount: Double
)