package com.example.practice_exam

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.practice_exam.presentation.ui.auth.LoginScreen
import com.example.practice_exam.presentation.ui.auth.RegisterScreen
import com.example.practice_exam.presentation.ui.auth.ResetPasswordScreen
import com.example.practice_exam.presentation.ui.detail.DetailScreen
import com.example.practice_exam.presentation.ui.home.HomeScreen
import com.example.practice_exam.presentation.viewmodel.AuthViewModel
import com.example.practice_exam.presentation.viewmodel.DetailViewModel
import com.example.practice_exam.presentation.viewmodel.HomeViewModel
import com.example.practice_exam.data.api.RetrofitInstance
import com.example.practice_exam.data.repository.MealRepositoryImpl
import com.example.practice_exam.data.local.AppDatabase
class MainActivity : ComponentActivity() {

    private lateinit var authViewModel: AuthViewModel
    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    navController = rememberNavController()
                    authViewModel = viewModel()

                    // Get the AppDatabase instance
                    val appDatabase = AppDatabase.getDatabase(this@MainActivity)

                    NavHost(
                        navController = navController,
                        startDestination = "login"
                    ) {
                        // Login Screen
                        composable("login") {
                            LoginScreen(
                                navController = navController,
                                viewModel = authViewModel,
                                activity = this@MainActivity, // Truyền Activity để hỗ trợ Google Sign-In
                                onResult = { isSuccess ->
                                    if (isSuccess) {
                                        navController.navigate("home") {
                                            popUpTo("login") { inclusive = true }
                                        }
                                    }
                                }
                            )
                        }

                        // Register Screen
                        composable("register") {
                            RegisterScreen(
                                navController = navController,
                                viewModel = authViewModel,
                                onResult = { isSuccess ->
                                    if (isSuccess) {
                                        navController.navigate("login")
                                    }
                                }
                            )
                        }

                        // Reset Password Screen
                        composable("reset_password") {
                            ResetPasswordScreen(
                                navController = navController,
                                viewModel = authViewModel,
                                onResult = { isSuccess ->
                                    if (isSuccess) {
                                        // Hiển thị thông báo hoặc hành động khác
                                    }
                                }
                            )
                        }

                        // Home Screen
                        composable("home") {
                            val mealApiService = RetrofitInstance.api
                            val repository = MealRepositoryImpl(mealApiService, appDatabase)
                            val viewModel = HomeViewModel(repository)

                            HomeScreen(navController = navController, viewModel = viewModel)
                        }

                        // Detail Screen
                        composable("detail/{mealId}") { backStackEntry ->
                            val mealId = backStackEntry.arguments?.getString("mealId") ?: ""
                            val mealApiService = RetrofitInstance.api
                            val repository = MealRepositoryImpl(mealApiService, appDatabase)
                            val viewModel = DetailViewModel(repository)

                            DetailScreen(navController = navController, mealId = mealId, viewModel = viewModel)
                        }
                    }
                }
            }
        }
    }
}