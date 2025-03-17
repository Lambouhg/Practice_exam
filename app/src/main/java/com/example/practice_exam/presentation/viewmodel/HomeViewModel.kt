package com.example.practice_exam.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practice_exam.data.repository.MealRepository
import com.example.practice_exam.domain.model.Category
import com.example.practice_exam.domain.model.Meal
import com.example.practice_exam.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: MealRepository) : ViewModel() {

    private val _categories = MutableStateFlow<Resource<List<Category>>>(Resource.Loading())
    val categories: StateFlow<Resource<List<Category>>> get() = _categories

    private val _meals = MutableStateFlow<Resource<List<Meal>>>(Resource.Loading())
    val meals: StateFlow<Resource<List<Meal>>> get() = _meals

    private val _searchedMeals = MutableStateFlow<Resource<List<Meal>>>(Resource.Loading())
    val searchedMeals: StateFlow<Resource<List<Meal>>> get() = _searchedMeals

    init {
        fetchCategories()
        fetchMeals()
    }

    private fun fetchCategories() {
        viewModelScope.launch {
            repository.getCategories().collect { resource ->
                _categories.value = resource
            }
        }
    }

    private fun fetchMeals() {
        viewModelScope.launch {
            repository.getMealsByCategory("Beef").collect { resource -> // Thay "Beef" bằng danh mục mặc định nếu cần
                _meals.value = resource
            }
        }
    }

    fun fetchMealsByCategory(categoryName: String) {
        viewModelScope.launch {
            repository.getMealsByCategory(categoryName).collect { resource ->
                _meals.value = resource
            }
        }
    }

    fun searchMeals(query: String) {
        viewModelScope.launch {
            repository.searchMeals(query).collect { resource ->
                _searchedMeals.value = resource
            }
        }
    }
}