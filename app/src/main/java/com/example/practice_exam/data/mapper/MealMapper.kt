package com.example.practice_exam.data.mapper

import com.example.practice_exam.data.local.entity.MealEntity
import com.example.practice_exam.domain.model.Meal

fun Meal.toMealEntity(): MealEntity {
    return MealEntity(
        id = id,
        name = name,
        imageUrl = imageUrl,
        instructions = instructions,
        youtubeUrl = youtubeUrl,
        ingredients = ingredients
    )
}

fun MealEntity.toMeal(): Meal {
    return Meal(
        id = id,
        name = name,
        imageUrl = imageUrl,
        instructions = instructions,
        youtubeUrl = youtubeUrl,
        ingredients = ingredients
    )
}