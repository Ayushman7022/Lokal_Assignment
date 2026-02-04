package com.example.operation_android.ui.auth

data class AuthUiState(
    val email: String = "",
    val otp: String = "",
    val message: String = "",
    val isOtpSent: Boolean = false,
    val isLoggedIn: Boolean = false
)
