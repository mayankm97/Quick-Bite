package com.example.quickbite.ui.navigation

import kotlinx.serialization.Serializable

// we don't use traditional navigation of string, we will use routes, when we apply this way of
// navigation, we need to apply serialization as well
@Serializable
object Login

@Serializable
object SignUp

@Serializable
object AuthScreen

@Serializable
object Home