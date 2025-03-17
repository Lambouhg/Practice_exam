package com.example.practice_exam.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import android.util.Log
import com.example.practice_exam.data.repository.MealRepositoryImpl
import com.example.practice_exam.domain.model.Meal
import com.example.practice_exam.util.Resource
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel(private val mealRepository: MealRepositoryImpl) : ViewModel() {

    private val _meal = MutableStateFlow<Resource<Meal>>(Resource.Loading())
    val meal: StateFlow<Resource<Meal>> = _meal

    fun fetchMealById(mealId: String) {
        viewModelScope.launch {
            _meal.value = Resource.Loading()
            try {
                // Collect the first emission from the Flow
                val mealResource = mealRepository.getMealById(mealId).firstOrNull()
                when (mealResource) {
                    is Resource.Success -> {
                        val meal = mealResource.data?.firstOrNull()
                        if (meal != null) {
                            _meal.value = Resource.Success(meal)
                        } else {
                            _meal.value = Resource.Error("Meal not found")
                        }
                    }
                    is Resource.Error -> {
                        _meal.value = Resource.Error(mealResource.message ?: "An unexpected error occurred")
                    }
                    else -> {
                        _meal.value = Resource.Error("Unexpected resource type")
                    }
                }
            } catch (e: Exception) {
                Log.e("DetailViewModel", "Error fetching meal", e)
                _meal.value = Resource.Error(e.message ?: "An unexpected error occurred")
            }
        }
    }

    // Optional: Function to add meal to cart
    fun addToCart(meal: Meal) {
        // Logic to add meal to cart
        Log.d("DetailViewModel", "Added meal to cart: ${meal.name}")
    }

    // Optional: Function to remove meal from cart
    fun removeFromCart(meal: Meal) {
        // Logic to remove meal from cart
        Log.d("DetailViewModel", "Removed meal from cart: ${meal.name}")
    }
}