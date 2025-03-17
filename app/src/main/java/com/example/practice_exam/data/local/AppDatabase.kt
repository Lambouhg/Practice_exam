package com.example.practice_exam.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.practice_exam.data.local.dao.MealDao
import com.example.practice_exam.data.local.dao.UserDao
import com.example.practice_exam.data.local.entity.MealEntity
import com.example.practice_exam.data.local.entity.UserEntity


import com.example.practice_exam.data.local.Converters

@Database(entities = [MealEntity::class, UserEntity::class], version = 1)
@TypeConverters(Converters::class) // Dùng Converters đúng
abstract class AppDatabase : RoomDatabase() {

    abstract fun mealDao(): MealDao
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "meal_database"
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
