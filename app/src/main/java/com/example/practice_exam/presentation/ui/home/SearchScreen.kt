package com.example.practice_exam.presentation.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.practice_exam.R
import com.example.practice_exam.domain.model.Meal
import com.example.practice_exam.presentation.viewmodel.HomeViewModel
import com.example.practice_exam.util.Resource

@Composable
fun SearchScreen(navController: NavController, viewModel: HomeViewModel) {
    var searchQuery by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text(stringResource(id = R.string.search_hint)) },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = {
                viewModel.searchMeals(searchQuery)
            }),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        val meals by viewModel.searchedMeals.collectAsState()

        when (meals) {
            is Resource.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = androidx.compose.ui.Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is Resource.Success -> {
                val mealList = (meals as Resource.Success<List<Meal>>).data
                if (mealList.isNullOrEmpty()) {
                    Text(text = "No meals found")
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
@Composable
fun SearchMealItem(meal: Meal, onClick: () -> Unit) {
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