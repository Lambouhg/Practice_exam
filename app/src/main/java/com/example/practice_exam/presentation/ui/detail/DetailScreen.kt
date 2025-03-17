package com.example.practice_exam.presentation.ui.detail

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.practice_exam.R
import com.example.practice_exam.domain.model.Meal
import com.example.practice_exam.presentation.viewmodel.DetailViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import com.example.practice_exam.util.Resource
import androidx.compose.ui.Alignment
import kotlinx.coroutines.launch

import androidx.compose.material3.SnackbarData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(navController: NavController, mealId: String, viewModel: DetailViewModel) {
    val meal by viewModel.meal.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() } // Create SnackbarHostState

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.meal_detail_title)) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) } // Add SnackbarHost
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            when (meal) {
                is Resource.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                is Resource.Success -> {
                    val mealData = meal.data
                    if (mealData != null) {
                        DetailContent(
                            meal = mealData,
                            viewModel = viewModel,
                            navController = navController,
                            snackbarHostState = snackbarHostState // Pass SnackbarHostState
                        )
                    } else {
                        Text(text = "Meal not found")
                    }
                }
                is Resource.Error -> {
                    Text(text = meal.message ?: "An error occurred")
                }
                else -> {
                    Text(text = "Unknown state")
                }
            }
        }
    }
}


@Composable
fun DetailContent(
    meal: Meal,
    viewModel: DetailViewModel,
    navController: NavController,
    snackbarHostState: SnackbarHostState
) {
    val scope = rememberCoroutineScope() // Create a coroutine scope for launching coroutines

    Column(modifier = Modifier.padding(16.dp)) {
        // Display the meal image
        AsyncImage(
            model = meal.imageUrl,
            contentDescription = meal.name,
            modifier = Modifier.size(200.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Display the meal name
        Text(text = meal.name, style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(8.dp))

        // Display the instructions (if available)
        if (!meal.instructions.isNullOrEmpty()) {
            Text(
                text = meal.instructions,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(16.dp))
        } else {
            Text(
                text = "No instructions available.",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Display the YouTube link (if available)
        if (!meal.youtubeUrl.isNullOrEmpty()) {
            Text(
                text = "Watch on YouTube:",
                style = MaterialTheme.typography.labelMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = meal.youtubeUrl,
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Display the list of ingredients (if available)
        if (!meal.ingredients.isNullOrEmpty()) {
            Text(
                text = "Ingredients:",
                style = MaterialTheme.typography.labelMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            meal.ingredients.forEach { ingredient ->
                Text(
                    text = "- $ingredient",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Add to cart button
        Button(onClick = {
            viewModel.addToCart(meal)
            scope.launch {
                val result = snackbarHostState.showSnackbar(
                    message = "Meal added to cart",
                    actionLabel = "Undo"
                )
                if (result == SnackbarResult.ActionPerformed) {
                    viewModel.removeFromCart(meal)
                }
            }
        }) {
            Text(text = "Add to Cart")
        }

    }
}