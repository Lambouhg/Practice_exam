package com.example.practice_exam.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://www.themealdb.com/api/json/v1/1/") // URL của TheMealDB API
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: MealApiService by lazy {
        retrofit.create(MealApiService::class.java)
    }
}
