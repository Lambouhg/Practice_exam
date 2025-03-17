// SearchMealsUseCase.kt
package com.example.practice_exam.domain.usecase

import com.example.practice_exam.domain.model.Meal
import com.example.practice_exam.data.repository.MealRepository
import com.example.practice_exam.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchMealsUseCase(private val mealRepository: MealRepository) {
    operator fun invoke(query: String): Flow<Resource<List<Meal>>> {
        return mealRepository.searchMeals(query)}
}