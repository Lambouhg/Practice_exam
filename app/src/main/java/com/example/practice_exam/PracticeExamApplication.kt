package com.example.practice_exam

import android.app.Application
import com.google.firebase.FirebaseApp

class PracticeExamApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }
}