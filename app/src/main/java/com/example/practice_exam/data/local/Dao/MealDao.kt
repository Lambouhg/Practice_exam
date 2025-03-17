package com.example.practice_exam.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.practice_exam.data.local.entity.MealEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MealDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeal(meal: MealEntity)

    @Query("SELECT * FROM meals WHERE id = :mealId")
    fun getMealById(mealId: String): Flow<MealEntity?>

    @Query("SELECT * FROM meals")
    fun getAllMeals(): Flow<List<MealEntity>>
}