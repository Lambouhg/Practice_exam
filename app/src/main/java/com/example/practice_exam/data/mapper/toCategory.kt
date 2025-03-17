// toCategory.kt
package com.example.practice_exam.data.mapper

import com.example.practice_exam.data.model.CategoryDto
import com.example.practice_exam.domain.model.Category

fun CategoryDto.toCategory(): Category {
    return Category(
        name = strCategory,
        description = strCategoryDescription,
        imageUrl = strCategoryThumb
    )
}