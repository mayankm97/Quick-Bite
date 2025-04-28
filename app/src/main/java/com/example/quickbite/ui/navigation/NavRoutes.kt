package com.example.quickbite.ui.navigation

import com.example.quickbite.data.models.FoodItem
import kotlinx.serialization.Serializable

interface NavRoute
// we don't use traditional navigation of string, we will use routes, when we apply this way of
// navigation, we need to apply serialization as well
@Serializable
object Login : NavRoute

@Serializable
object SignUp : NavRoute

@Serializable
object AuthScreen : NavRoute

@Serializable
object Home : NavRoute

@Serializable
data class RestaurantDetails(
    val restaurantId: String,
    val restaurantName: String,
    val restaurantImageUrl: String
) : NavRoute

@Serializable
data class FoodDetails(val foodItem: FoodItem) : NavRoute

@Serializable
object Cart : NavRoute

@Serializable
object Notification : NavRoute

@Serializable
object AddressList : NavRoute