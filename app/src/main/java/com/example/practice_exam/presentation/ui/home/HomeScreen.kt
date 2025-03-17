package com.example.practice_exam.presentation.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.practice_exam.R
import com.example.practice_exam.domain.model.Category
import com.example.practice_exam.domain.model.Meal
import com.example.practice_exam.presentation.viewmodel.HomeViewModel
import com.example.practice_exam.util.Resource
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import com.example.practice_exam.presentation.ui.MealItem
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewModel) {
    val categories by viewModel.categories.collectAsState()
    val meals by viewModel.meals.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.app_name)) },
                actions = {
                    IconButton(onClick = { navController.navigate("search") }) {
                        Icon(Icons.Default.Search, contentDescription = "Search")
                    }
                    IconButton(onClick = { navController.navigate("profile") }) {
                        Icon(Icons.Default.Person, contentDescription = "Profile")
                    }
                    IconButton(onClick = { navController.navigate("cart") }) {
                        Icon(Icons.Default.ShoppingCart, contentDescription = "Cart")
                    }
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            // Display Categories
            when (categories) {
                is Resource.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                is Resource.Success -> {
                    val categoryList = (categories as Resource.Success<List<Category>>).data
                    if (categoryList.isNullOrEmpty()) {
                        Text(text = "No categories available")
                    } else {
                        LazyVerticalGrid(columns = GridCells.Fixed(2)) {
                            items(categoryList) { category ->
                                CategoryItem(category = category, onClick = {
                                    viewModel.fetchMealsByCategory(category.name)
                                })
                            }
                        }
                    }
                }
                is Resource.Error -> {
                    Text(text = (categories as Resource.Error<List<Category>>).message ?: "An error occurred")
                }
            }

            // Display Meals
            when (meals) {
                is Resource.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                is Resource.Success -> {
                    val mealList = (meals as Resource.Success<List<Meal>>).data
                    if (mealList.isNullOrEmpty()) {
                        Text(text = "No meals available")
                    } else {
                        LazyVerticalGrid(columns = GridCells.Fixed(2)) {
                            items(mealList) { meal ->
                                MealItem(meal = meal, onClick = {
                                    navController.navigate("detail/${meal.id}")
                                })
                            }
                        }
                    }
                }
                is Resource.Error -> {
                    Text(text = (meals as Resource.Error<List<Meal>>).message ?: "An error occurred")
                }
            }
        }
    }
}

@Composable
fun CategoryItem(category: Category, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .clickable(onClick = onClick)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            AsyncImage(
                model = category.imageUrl,
                contentDescription = category.name,
                modifier = Modifier.size(100.dp)
            )
            Text(text = category.name)
        }
    }
}

@Composable
fun MealItem(meal: Meal, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .clickable(onClick = onClick)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            AsyncImage(
                model = meal.imageUrl,
                contentDescription = meal.name,
                modifier = Modifier.size(100.dp)
            )
            Text(text = meal.name)
        }
    }
}