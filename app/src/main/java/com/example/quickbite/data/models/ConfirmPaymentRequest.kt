package com.example.quickbite.data.models

data class ConfirmPaymentRequest(
    val paymentIntentId: String,
    val addressId: String
)