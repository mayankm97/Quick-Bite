package com.example.quickbite.ui.features.auth.login

import android.content.Context
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.credentials.CredentialManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quickbite.data.FoodApi
import com.example.quickbite.data.FoodHubSession
import com.example.quickbite.data.auth.GoogleAuthUIProvider
import com.example.quickbite.data.models.OAuthRequest
import com.example.quickbite.data.models.SignInRequest
import com.example.quickbite.data.models.SignUpRequest
import com.example.quickbite.data.remote.ApiResponse
import com.example.quickbite.data.remote.safeApiCall
import com.example.quickbite.ui.features.auth.BaseAuthViewModel
import com.example.quickbite.ui.features.auth.signup.SignUpViewModel.SignupEvent
import com.example.quickbite.ui.features.auth.signup.SignUpViewModel.SignupNavigationEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SignInViewModel @Inject constructor(override val foodApi: FoodApi, val session: FoodHubSession) : BaseAuthViewModel(foodApi) {

    private val _uiState = MutableStateFlow<SignInEvent>(SignInEvent.Nothing)
    val uiState = _uiState.asStateFlow()

    // to navigate to different screens and creating events for each
    private val _navigationEvent = MutableSharedFlow<SignInNavigationEvent>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    fun onEmailChange(email: String) {
        _email.value = email
    }
    fun onPasswordChange(password: String) {
        _password.value = password
    }
    fun onSignInClick() {
        viewModelScope.launch {
            _uiState.value = SignInEvent.Loading
                val response = safeApiCall { foodApi.signIn(
                    SignInRequest(
                        email = email.value,
                        password = password.value
                    )
                )
                }
                when (response) {
                    is ApiResponse.Success -> {
                        _uiState.value = SignInEvent.Success
                        session.storeToken(response.data.token)
                        _navigationEvent.emit(SignInNavigationEvent.NavigateToHome)
                    }
                    else -> {
                        var errr = (response as? ApiResponse.Error)?.code ?: 0
                        error = "Sign In failed"
                        errorDescription = "Failed to sign up"
                        when (errr) {
                            400 -> {
                                error = "Invalid credentials"
                                errorDescription = "Please enter correct details"
                            }
                        }
                        _uiState.value = SignInEvent.Error
                    }
                }
        }
    }

    fun onSignUpClicked() {
        viewModelScope.launch {
            _navigationEvent.emit(SignInNavigationEvent.NavigateToSignUp)
        }
    }

    sealed class SignInNavigationEvent {
        object NavigateToSignUp : SignInNavigationEvent()
        object NavigateToHome : SignInNavigationEvent()
    }
    sealed class SignInEvent {
        object Nothing : SignInEvent()
        object Success : SignInEvent()
        object Error : SignInEvent()
        object Loading : SignInEvent()
    }

    override fun loading() {
        viewModelScope.launch {
            _uiState.value = SignInEvent.Loading
        }
    }

    override fun onGoogleError(msg: String) {
        viewModelScope.launch {
            errorDescription = msg
            error = "Google sign in failed"
            _uiState.value = SignInEvent.Error
        }
    }

    override fun onSocialLoginSuccess(token: String) {
        viewModelScope.launch {
            session.storeToken(token)
            _uiState.value = SignInEvent.Success
            _navigationEvent.emit(SignInNavigationEvent.NavigateToHome)
        }
    }

}