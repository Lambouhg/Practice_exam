package com.example.practice_exam.data.repository

import com.example.practice_exam.data.local.entity.UserEntity
import com.example.practice_exam.domain.model.Category
import com.example.practice_exam.domain.model.Meal
import com.example.practice_exam.util.Resource
import kotlinx.coroutines.flow.Flow
interface MealRepository {
    fun getCategories(): Flow<Resource<List<Category>>>
    fun getMealsByCategory(category: String): Flow<Resource<List<Meal>>>
    fun searchMeals(query: String): Flow<Resource<List<Meal>>>
    fun getMealById(mealId: String): Flow<Resource<List<Meal>>>
    fun addMealToFavorites(meal: Meal): Flow<Resource<Boolean>>
    fun registerUser(user: UserEntity): Flow<Resource<Boolean>>
    suspend fun loginUser(email: String, password: String): Resource<UserEntity?>
    fun getMeals(): Flow<Resource<List<Meal>>>
}