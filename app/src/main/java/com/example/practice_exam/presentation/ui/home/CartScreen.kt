package com.example.practice_exam.presentation.ui.home


import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.practice_exam.R
import com.example.practice_exam.domain.model.Meal
import com.example.practice_exam.presentation.viewmodel.CartViewModel
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(navController: NavController, viewModel: CartViewModel) {
    val cartItems: List<Meal> by viewModel.cartItems.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Your Cart") })
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            if (cartItems.isEmpty()) {
                Text(text = "Your cart is empty")
            } else {
                LazyVerticalGrid(columns = GridCells.Fixed(2)) {
                    items(cartItems) { meal ->
                        CartItem(
                            meal = meal,
                            onClick = { navController.navigate("detail/${meal.id}") },
                            onRemove = { viewModel.removeFromCart(meal) }
                        )
                    }
                }
                Button(onClick = { viewModel.clearCart() }) {
                    Text("Clear Cart")
                }
            }
        }
    }
}

@Composable
fun CartItem(meal: Meal, onClick: () -> Unit, onRemove: () -> Unit) {
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
            IconButton(onClick = onRemove) {
                Icon(Icons.Default.Delete, contentDescription = "Remove from Cart")
            }
        }
    }
}