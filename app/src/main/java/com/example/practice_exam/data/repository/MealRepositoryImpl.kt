package com.example.practice_exam.data.repository

import com.example.practice_exam.data.api.MealApiService
import com.example.practice_exam.data.local.AppDatabase

import com.example.practice_exam.data.local.entity.UserEntity
import com.example.practice_exam.data.mapper.toCategory
import com.example.practice_exam.data.mapper.toMeal
import com.example.practice_exam.data.mapper.toMealEntity // Đảm bảo import đúng
import com.example.practice_exam.domain.model.Category
import com.example.practice_exam.domain.model.Meal
import com.example.practice_exam.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class MealRepositoryImpl @Inject constructor(
    private val mealApiService: MealApiService,
    private val appDatabase: AppDatabase
) : MealRepository {

    override fun getCategories(): Flow<Resource<List<Category>>> = flow {
        emit(Resource.Loading())
        try {
            val response = mealApiService.getCategories()
            val categories = response.categories.map { it.toCategory() }
            emit(Resource.Success(categories))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "An unexpected error occurred"))
        }
    }

    override fun getMealsByCategory(category: String): Flow<Resource<List<Meal>>> = flow {
        emit(Resource.Loading())
        try {
            val response = mealApiService.getMealsByCategory(category)
            val meals = response.meals.map { it.toMeal() }
            emit(Resource.Success(meals))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "An unexpected error occurred"))
        }
    }

    override fun searchMeals(query: String): Flow<Resource<List<Meal>>> = flow {
        emit(Resource.Loading())
        try {
            val response = mealApiService.searchMeals(query)
            val meals = response.meals.map { it.toMeal() }
            emit(Resource.Success(meals))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "An unexpected error occurred"))
        }
    }

    override fun getMealById(mealId: String): Flow<Resource<List<Meal>>> = flow {
        emit(Resource.Loading())
        try {
            val response = mealApiService.getMealDetail(mealId)
            val meals = response.meals.map { it.toMeal() }
            emit(Resource.Success(meals))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "An unexpected error occurred"))
        }
    }

    override fun addMealToFavorites(meal: Meal) = flow {
        try {
            val mealEntity = meal.toMealEntity() // Phương thức này sẽ được tìm thấy
            appDatabase.mealDao().insertMeal(mealEntity)
            emit(Resource.Success(true))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "An unexpected error occurred"))
        }
    }

    override fun registerUser(user: UserEntity) = flow {
        try {
            appDatabase.userDao().insertUser(user)
            emit(Resource.Success(true))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "An unexpected error occurred"))
        }
    }

    override suspend fun loginUser(email: String, password: String): Resource<UserEntity?> {
        return try {
            val user = appDatabase.userDao().getUserByEmailAndPassword(email, password)
            Resource.Success(user)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An unexpected error occurred")
        }
    }

    override fun getMeals(): Flow<Resource<List<Meal>>> = flow {
        emit(Resource.Loading())
        try {
            val response = mealApiService.getMealsByCategory("Beef") // Thay "Beef" bằng danh mục mặc định nếu cần
            val meals = response.meals.map { it.toMeal() }
            emit(Resource.Success(meals))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "An unexpected error occurred"))
        }
    }
}