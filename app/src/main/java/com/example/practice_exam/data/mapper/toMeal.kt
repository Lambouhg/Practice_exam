// toMeal.kt
package com.example.practice_exam.data.mapper

import com.example.practice_exam.data.model.MealDetailDto
import com.example.practice_exam.data.model.MealDto
import com.example.practice_exam.domain.model.Meal

fun MealDto.toMeal(): Meal {
    return Meal(
        id = idMeal,
        name = strMeal,
        imageUrl = strMealThumb
    )
}

fun MealDetailDto.toMeal(): Meal {
    return Meal(
        id = idMeal,
        name = strMeal,
        imageUrl = strMealThumb,
        instructions = strInstructions,
        youtubeUrl = strYoutube,
        ingredients = listOfNotNull(
            strIngredient1,
            strIngredient2,
            strIngredient3
            // ... các thành phần khác
        )
    )
}