package com.example.practice_exam.data.api

import com.example.practice_exam.data.model.CategoryResponse
import com.example.practice_exam.data.model.MealDetailResponse
import com.example.practice_exam.data.model.MealResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApiService {
    @GET("categories.php")
    suspend fun getCategories(): CategoryResponse

    @GET("filter.php")
    suspend fun getMealsByCategory(@Query("c") category: String): MealResponse

    @GET("lookup.php")
    suspend fun getMealDetail(@Query("i") id: String): MealDetailResponse

    @GET("search.php")
    suspend fun searchMeals(@Query("s") query: String): MealResponse
}