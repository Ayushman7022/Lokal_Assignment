package com.example.operation_android.ui.auth

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.operation_android.viewModel.AuthViewModel

@Composable
fun OtpScreen(
    authViewModel: AuthViewModel,
    onOtpVerified: () -> Unit,
    onBack: () -> Unit
) {
    val uiState by authViewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "OTP sent to",
            style = MaterialTheme.typography.labelMedium
        )

        Text(
            text = uiState.email,
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Enter 6-digit OTP",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = uiState.otp,
            onValueChange = {
                val filtered = it.filter { ch -> ch.isDigit() }.take(6)
                authViewModel.onOtpChanged(filtered)
            },
            label = { Text("OTP") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "${uiState.otp.length}/6 digits",
            style = MaterialTheme.typography.labelSmall
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val success = authViewModel.verifyOtp()
                if (success) onOtpVerified()
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = uiState.otp.length == 6
        ) {
            Text("Verify OTP")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            TextButton(onClick = {
                authViewModel.sendOtp()
            }) {
                Text("Resend OTP")
            }

            TextButton(onClick = onBack) {
                Text("Change Email")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (uiState.message.isNotBlank()) {
            Text(
                text = uiState.message,
                color = if (uiState.isLoggedIn)
                    MaterialTheme.colorScheme.primary
                else
                    MaterialTheme.colorScheme.error,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}
