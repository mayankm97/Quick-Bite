package com.example.quickbite.ui.features.auth.signup

import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quickbite.data.FoodApi
import com.example.quickbite.data.FoodHubSession
import com.example.quickbite.data.models.SignUpRequest
import com.example.quickbite.data.remote.ApiResponse
import com.example.quickbite.data.remote.safeApiCall
import com.example.quickbite.ui.features.auth.AuthScreenViewModel.AuthEvent
import com.example.quickbite.ui.features.auth.BaseAuthViewModel
import com.example.quickbite.ui.features.auth.login.SignInViewModel.SignInEvent
import com.example.quickbite.ui.features.auth.login.SignInViewModel.SignInNavigationEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(override val foodApi: FoodApi, val session: FoodHubSession) : BaseAuthViewModel(foodApi) {
    private val _uiState = MutableStateFlow<SignupEvent>(SignupEvent.Nothing)
    val uiState = _uiState.asStateFlow()

    // to navigate to different screens and creating events for each
    private val _navigationEvent = MutableSharedFlow<SignupNavigationEvent>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    private val _name = MutableStateFlow("")
    val name = _name.asStateFlow()

    fun onEmailChange(email: String) {
        _email.value = email
    }
    fun onPasswordChange(password: String) {
        _password.value = password
    }
    fun onNameChange(name: String) {
        _name.value = name
    }
    fun onSignupClick() {
        viewModelScope.launch {
            _uiState.value = SignupEvent.Loading
            try {
                val response = safeApiCall { foodApi.signUp(
                    SignUpRequest(
                        name = name.value,
                        email = email.value,
                        password = password.value
                    )
                )
            }
                when (response) {
                    is ApiResponse.Success -> {
                        _uiState.value = SignupEvent.Success
                        session.storeToken(response.data.token)
                        _navigationEvent.emit(SignupNavigationEvent.NavigateToHome)
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
                        _uiState.value = SignupEvent.Error
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _uiState.value = SignupEvent.Error
            }
        }
    }

    fun onLoginClicked() {
        viewModelScope.launch {
            _navigationEvent.emit(SignupNavigationEvent.NavigateToLogin)
        }
    }

    override fun loading() {
        viewModelScope.launch {
            _uiState.value = SignupEvent.Loading
        }
    }

    override fun onGoogleError(msg: String) {
        viewModelScope.launch {
            errorDescription = msg
            error = "Google sign in failed"
            _uiState.value = SignupEvent.Error
        }
    }

    override fun onSocialLoginSuccess(token: String) {
        viewModelScope.launch {
            session.storeToken(token)
            _uiState.value = SignupEvent.Success
            _navigationEvent.emit(SignupNavigationEvent.NavigateToHome)
        }
    }

    sealed class SignupNavigationEvent {
        object NavigateToLogin : SignupNavigationEvent()
        object NavigateToHome : SignupNavigationEvent()
    }
    sealed class SignupEvent {
        object Nothing : SignupEvent()
        object Success : SignupEvent()
        object Error : SignupEvent()
        object Loading : SignupEvent()
    }
}