package com.example.practice_exam.util

import android.app.Activity
import android.content.Intent
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.example.practice_exam.presentation.viewmodel.AuthViewModel

object GoogleSignInHelper {

    private const val RC_SIGN_IN = 9001

    // Khởi tạo Google Sign-In Client
    private fun getGoogleSignInClient(activity: Activity): GoogleSignInClient {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("181493268181-gd076qli2a4os19asml1ggqsqrdjulbv.apps.googleusercontent.com")
            .requestEmail()
            .build()

        return GoogleSignIn.getClient(activity, gso)
    }

    // Hàm khởi động Google Sign-In
    fun signInWithGoogle(
        activity: Activity,
        googleSignInLauncher: ManagedActivityResultLauncher<Intent, ActivityResult>
    ) {
        val googleSignInClient = getGoogleSignInClient(activity)
        val signInIntent = googleSignInClient.signInIntent
        googleSignInLauncher.launch(signInIntent) // Sử dụng launcher được truyền từ MainActivity
    }

    // Xử lý kết quả từ Google Sign-In
    fun handleSignInResult(
        data: Intent?,
        viewModel: AuthViewModel,
        onResult: (Boolean) -> Unit
    ) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        try {
            val account = task.getResult(ApiException::class.java)
            val idToken = account?.idToken
            if (idToken != null) {
                viewModel.signInWithGoogle(idToken, onResult)
            } else {
                println("Google Sign-In thất bại: ID Token is null")
                onResult(false)
            }
        } catch (e: ApiException) {
            println("Google Sign-In failed: ${e.statusCode} - ${e.message}")
            when (e.statusCode) {
                12500 -> println("Sign-in required")
                10 -> println("Network error")
                8 -> println("Developer error")
                16 -> println("Internal error")
                17 -> println("API not available")
                else -> println("Unknown error: ${e.message}")
            }
            onResult(false)
        }
    }
}