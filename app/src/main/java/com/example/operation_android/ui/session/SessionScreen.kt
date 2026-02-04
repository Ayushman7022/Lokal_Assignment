package com.example.operation_android.ui.session

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.operation_android.viewModel.SessionViewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun SessionScreen(
    sessionViewModel: SessionViewModel,
    onLogout: () -> Unit
) {
    val uiState by sessionViewModel.uiState.collectAsState()

    val minutes = uiState.elapsedSeconds / 60
    val seconds = uiState.elapsedSeconds % 60

    val startTimeFormatted = remember(uiState.startTimeMillis) {
        SimpleDateFormat("HH:mm:ss", Locale.getDefault())
            .format(Date(uiState.startTimeMillis))
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Session Started At",
            style = MaterialTheme.typography.titleMedium
        )

        Text(
            text = startTimeFormatted,
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Session Duration",
            style = MaterialTheme.typography.titleMedium
        )

        Text(
            text = String.format("%02d:%02d", minutes, seconds),
            style = MaterialTheme.typography.displaySmall
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onLogout,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Logout")
        }
    }
}
