package com.example.quickbite.ui.feature.food_item_details

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quickbite.data.FoodApi
import com.example.quickbite.data.models.AddToCartRequest
import com.example.quickbite.data.remote.ApiResponse
import com.example.quickbite.data.remote.safeApiCall
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FoodDetailsViewModel @Inject constructor(val foodApi: FoodApi) : ViewModel() {

    private val _uiState = MutableStateFlow<FoodDetailsUiState>(FoodDetailsUiState.Nothing)
    val uiState = _uiState.asStateFlow()

    private val _event = MutableSharedFlow<FoodDetailsEvent>()
    val event = _event.asSharedFlow()

    private val _quantity = MutableStateFlow<Int>(1)
    val quantity = _quantity.asStateFlow()

    fun incrementQuantity() {
        if (quantity.value == 5) {
            return
        }
        _quantity.value += 1
    }

    fun decrementQuantity() {
        if (_quantity.value == 1) {
            return
        }
        _quantity.value -= 1
    }

    fun addToCart(restaurantId: String, foodItemId: String) {
        viewModelScope.launch {
            _uiState.value = FoodDetailsUiState.Loading
            Log.d("AddToCart", "Request - restaurantId: $restaurantId, foodItemId: $foodItemId, quantity: ${quantity.value}")

            val response = safeApiCall {
                foodApi.addToCart(
                    AddToCartRequest(
                        restaurantId = restaurantId,
                        menuItemId = foodItemId,
                        quantity = quantity.value
                    )
                )
            }
            Log.d("AddToCart", "API Response: $response")

            when (response) {
                is ApiResponse.Success -> {
                    Log.d("AddToCart", "Item successfully added to cart")

                    _uiState.value = FoodDetailsUiState.Nothing
                    _event.emit(FoodDetailsEvent.onAddToCart)
                }

                is ApiResponse.Error -> {
                    Log.e("AddToCart", "Error adding to cart: ${response.message}")

                    _uiState.value = FoodDetailsUiState.Error(response.message)
                    _event.emit(FoodDetailsEvent.showErrorDialog(response.message))
                }

                else -> {
                    Log.e("AddToCart", "Unknown error")

                    _uiState.value = FoodDetailsUiState.Error("Unknown error")
                    _event.emit(FoodDetailsEvent.showErrorDialog("Unknown error"))
                }
            }
        }
    }

    fun goToCart() {
        viewModelScope.launch {
            _event.emit(FoodDetailsEvent.goToCart)
        }
    }

    sealed class FoodDetailsUiState {
        object Nothing : FoodDetailsUiState()
        object Loading : FoodDetailsUiState()
        data class Error(val message: String) : FoodDetailsUiState()
    }

    sealed class FoodDetailsEvent {
        data class showErrorDialog(val message: String) : FoodDetailsEvent()
        object onAddToCart : FoodDetailsEvent()
        object goToCart : FoodDetailsEvent()
    }
}