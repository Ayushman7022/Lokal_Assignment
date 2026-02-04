package com.example.operation_android.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.operation_android.analytics.AnalyticsLogger
import com.example.operation_android.ui.auth.LoginScreen
import com.example.operation_android.ui.auth.OtpScreen
import com.example.operation_android.ui.session.SessionScreen
import com.example.operation_android.viewModel.AuthViewModel
import com.example.operation_android.viewModel.SessionViewModel

object Routes {
    const val LOGIN = "login"
    const val OTP = "otp"
    const val SESSION = "session"
}

@Composable
fun AppNavigation(
    analyticsLogger: AnalyticsLogger
) {
    val navController = rememberNavController()

    val authViewModel: AuthViewModel = viewModel()
    val sessionViewModel: SessionViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = Routes.LOGIN
    ) {

        composable(Routes.LOGIN) {
            LoginScreen(
                authViewModel = authViewModel,
                onOtpSent = {
                    analyticsLogger.logOtpGenerated(authViewModel.uiState.value.email)
                    navController.navigate(Routes.OTP)
                }
            )
        }

        composable(Routes.OTP) {
            OtpScreen(
                authViewModel = authViewModel,
                onOtpVerified = {
                    analyticsLogger.logOtpSuccess(authViewModel.uiState.value.email)
                    navController.navigate(Routes.SESSION) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                },
                onBack = { navController.popBackStack() }
            )
        }

        composable(Routes.SESSION) {
            SessionScreen(
                sessionViewModel = sessionViewModel,
                onLogout = {
                    analyticsLogger.logLogout()
                    authViewModel.reset()
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(Routes.SESSION) { inclusive = true }
                    }
                }
            )
        }
    }
}
