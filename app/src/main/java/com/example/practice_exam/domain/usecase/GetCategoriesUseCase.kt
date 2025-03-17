package com.example.practice_exam.domain.usecase

import com.example.practice_exam.domain.model.Category
import com.example.practice_exam.data.repository.MealRepository
import com.example.practice_exam.util.Resource
import kotlinx.coroutines.flow.Flow

class GetCategoriesUseCase(private val mealRepository: MealRepository) {
    operator fun invoke(): Flow<Resource<List<Category>>> {
        return mealRepository.getCategories()
    }
}