package com.example.practice_exam.presentation.ui.auth

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.practice_exam.presentation.viewmodel.AuthViewModel
import com.example.practice_exam.util.GoogleSignInHelper

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: AuthViewModel,
    activity: Activity,
    onResult: (Boolean) -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    val googleSignInLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            GoogleSignInHelper.handleSignInResult(result.data, viewModel) { isSuccess ->
                if (isSuccess) {
                    onResult(true)
                } else {
                    errorMessage = "Google Sign-In failed. Please try again."
                }
            }
        } else {
            println("Google Sign-In thất bại")
            errorMessage = "Google Sign-In failed. Please try again."
        }
    }

    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") }
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        Button(onClick = {
            if (email.isEmpty() || password.isEmpty()) {
                errorMessage = "Email and password must not be empty"
            } else {
                viewModel.login(email, password) { isSuccess ->
                    if (isSuccess) {
                        onResult(true)
                    } else {
                        errorMessage = "Login failed. Please check your credentials."
                    }
                }
            }
        }) {
            Text("Login with Email")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = { GoogleSignInHelper.signInWithGoogle(activity, googleSignInLauncher) }) {
            Text("Login with Google")
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(onClick = { navController.navigate("reset_password") }) {
            Text("Forgot Password?")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
            Text("Don't have an account?")
            TextButton(onClick = { navController.navigate("register") }) {
                Text("Register")
            }
        }
    }
}