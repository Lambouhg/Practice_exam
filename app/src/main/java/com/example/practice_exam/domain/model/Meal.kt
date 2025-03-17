// Meal.kt
package com.example.practice_exam.domain.model

data class Meal(
    val id: String,
    val name: String,
    val imageUrl: String,
    val instructions: String? = null,
    val youtubeUrl: String? = null,
    val ingredients: List<String>? = null
)
