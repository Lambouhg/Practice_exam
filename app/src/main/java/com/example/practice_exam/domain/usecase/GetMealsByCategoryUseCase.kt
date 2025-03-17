package com.example.practice_exam.domain.usecase

import com.example.practice_exam.domain.model.Meal
import com.example.practice_exam.data.repository.MealRepository
import com.example.practice_exam.util.Resource
import kotlinx.coroutines.flow.Flow

class GetMealsByCategoryUseCase(private val mealRepository: MealRepository) {
    operator fun invoke(category: String): Flow<Resource<List<Meal>>> {
        return mealRepository.getMealsByCategory(category)
    }
}