package com.example.quickbite.ui.features.add_address

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quickbite.data.FoodApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AddAddressViewModel @Inject constructor(val foodApi: FoodApi):ViewModel(){
    private val _uiState = MutableStateFlow<AddAddressState>(AddAddressState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _event = MutableSharedFlow<AddAddressEvent>()
    val event = _event.asSharedFlow()



    fun reverseGeocode(lat: Double, lon: Double) {


    }
    fun onAddAddressClicked() {

    }


    sealed class AddAddressEvent {
        object NavigateToAddressList : AddAddressEvent()
    }

    sealed class AddAddressState {
        object Loading : AddAddressState()
        object Success : AddAddressState()
        object AddressStoring : AddAddressState()
        data class Error(val message: String) : AddAddressState()
    }
}