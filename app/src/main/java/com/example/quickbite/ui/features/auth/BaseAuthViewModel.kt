package com.example.quickbite.ui.features.auth

import android.util.Log
import androidx.activity.ComponentActivity
import androidx.credentials.CredentialManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quickbite.data.FoodApi
import com.example.quickbite.data.auth.GoogleAuthUIProvider
import com.example.quickbite.data.models.AuthResponse
import com.example.quickbite.data.models.OAuthRequest
import com.example.quickbite.data.remote.ApiResponse
import com.example.quickbite.data.remote.safeApiCall
import com.example.quickbite.ui.features.auth.login.SignInViewModel.SignInEvent
import com.example.quickbite.ui.features.auth.login.SignInViewModel.SignInNavigationEvent
import kotlinx.coroutines.launch

abstract class BaseAuthViewModel(open val foodApi: FoodApi) : ViewModel() {
    var error: String = ""
    var errorDescription: String = ""
    private val googleAuthUIProvider = GoogleAuthUIProvider()

    abstract fun loading()
    abstract fun onGoogleError(msg: String)
    abstract fun onSocialLoginSuccess(token: String)

    fun onGoogleClicked(context: ComponentActivity) {
        initiateGoogleLogin(context)
    }
    protected fun initiateGoogleLogin(context: ComponentActivity) {
        viewModelScope.launch {
            loading()
            try {
                val response = googleAuthUIProvider.signIn(
                    context,
                    CredentialManager.create(context)
                )

                fetchFoodAppToken(response.token, "google") {
                    onGoogleError(it)
                }
            } catch (e : Throwable) {
                onGoogleError(e.message.toString())
            }
        }
    }

    fun fetchFoodAppToken(token: String, provider: String, onError : (String) -> Unit) {
        viewModelScope.launch {
            val request = OAuthRequest(
                token, provider
            )
            val res = safeApiCall { foodApi.oAuth(request) }
            when (res) {
                is ApiResponse.Success -> {
                    onSocialLoginSuccess(res.data.token)
                }
                else -> {
                    val error = (res as? ApiResponse.Error)?.code
                    if (error != null) {
                        when (error) {
                            401 -> onError("Invalid Token")
                            500 -> onError("Server Error")
                            404 -> onError("Not Found")
                            else -> onError("Failed")
                        }
                    } else {
                        onError("Failed")
                    }
                }
            }
        }
    }
}