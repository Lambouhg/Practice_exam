// MealDetailDto.kt
package com.example.practice_exam.data.model

data class MealDetailDto(
    val idMeal: String,
    val strMeal: String,
    val strMealThumb: String,
    val strInstructions: String,
    val strYoutube: String,
    val strIngredient1: String?,
    val strIngredient2: String?,
    val strIngredient3: String?,
    // ... các thành phần khác
)