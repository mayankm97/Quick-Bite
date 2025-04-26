package com.example.quickbite.data

import android.content.Context
import android.content.SharedPreferences


class FoodHubSession(val context: Context) {
    val sharedPres: SharedPreferences =
        context.getSharedPreferences("foodhub", Context.MODE_PRIVATE)

    fun storeToken(token: String) {
        sharedPres.edit().putString("token", token).apply()
    }

    fun getToken(): String? {
        sharedPres.getString("token", null)?.let {
            return it
        }
        return null
    }
}