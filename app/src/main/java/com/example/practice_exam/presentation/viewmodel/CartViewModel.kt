package com.example.practice_exam.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.practice_exam.domain.model.Meal
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class CartViewModel : ViewModel() {

    // Private MutableStateFlow to hold the cart items
    private val _cartItems = MutableStateFlow<List<Meal>>(emptyList())
    // Public StateFlow for observing cart items
    val cartItems: StateFlow<List<Meal>> get() = _cartItems

    /**
     * Adds a meal to the cart.
     * @param meal The meal to add.
     */
    fun addToCart(meal: Meal) {
        _cartItems.update { currentCart ->
            // Prevent duplicate meals (optional)
            if (currentCart.any { it.id == meal.id }) {
                currentCart // Do not add duplicate meals
            } else {
                currentCart + meal
            }
        }
    }

    /**
     * Removes a meal from the cart.
     * @param meal The meal to remove.
     */
    fun removeFromCart(meal: Meal) {
        _cartItems.update { currentCart ->
            currentCart.filter { it.id != meal.id }
        }
    }

    /**
     * Clears all items from the cart.
     */
    fun clearCart() {
        _cartItems.update { emptyList() }
    }
}