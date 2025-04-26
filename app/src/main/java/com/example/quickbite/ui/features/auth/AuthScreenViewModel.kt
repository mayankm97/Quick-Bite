package com.example.quickbite.ui.features.auth

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.lifecycle.viewModelScope
import com.example.quickbite.data.FoodApi
import com.example.quickbite.data.FoodHubSession
import com.example.quickbite.data.models.SignInRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthScreenViewModel @Inject constructor(override val foodApi: FoodApi, val session: FoodHubSession) : BaseAuthViewModel(foodApi) {

    override fun loading() {
        viewModelScope.launch {
            _uiState.value = AuthEvent.Loading
        }
    }

    override fun onGoogleError(msg: String) {
        viewModelScope.launch {
            errorDescription = msg
            error = "Google sign in failed"
            _uiState.value = AuthEvent.Error
            _navigationEvent.emit(AuthNavigationEvent.ShowErrorDialog)
        }
    }

    override fun onSocialLoginSuccess(token: String) {
        viewModelScope.launch {
            _uiState.value = AuthEvent.Success
            _navigationEvent.emit(AuthNavigationEvent.NavigateToHome)
        }
    }

    private val _uiState = MutableStateFlow<AuthEvent>(AuthEvent.Nothing)
    val uiState = _uiState.asStateFlow()

    // to navigate to different screens and creating events for each
    private val _navigationEvent = MutableSharedFlow<AuthNavigationEvent>()
    val navigationEvent = _navigationEvent.asSharedFlow()



    sealed class AuthNavigationEvent {
        object NavigateToSignUp : AuthNavigationEvent()
        object NavigateToHome : AuthNavigationEvent()
        object ShowErrorDialog: AuthNavigationEvent()
    }
    sealed class AuthEvent {
        object Nothing : AuthEvent()
        object Success : AuthEvent()
        object Error : AuthEvent()
        object Loading : AuthEvent()
    }
}