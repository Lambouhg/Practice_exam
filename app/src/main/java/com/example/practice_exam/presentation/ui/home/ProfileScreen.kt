package com.example.practice_exam.presentation.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.practice_exam.R
import com.example.practice_exam.presentation.viewmodel.ProfileViewModel
import com.google.firebase.auth.FirebaseUser
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController, viewModel: ProfileViewModel) {
    val user: FirebaseUser? by viewModel.user.collectAsState(initial = null)

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(stringResource(id = R.string.profile_title)) })
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            if (user != null) {
                Text(text = "Name: ${user?.displayName}")
                Text(text = "Email: ${user?.email}")
                // Thêm các thông tin người dùng khác nếu cần
            } else {
                Text(text = "User not logged in")
            }
        }
    }
}