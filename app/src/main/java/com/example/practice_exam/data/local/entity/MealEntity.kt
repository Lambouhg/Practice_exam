package com.example.practice_exam.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "meals")
data class MealEntity(
    @PrimaryKey val id: String,
    val name: String,
    val imageUrl: String,
    val instructions: String?,
    val youtubeUrl: String?,
    val ingredients: List<String>?
)