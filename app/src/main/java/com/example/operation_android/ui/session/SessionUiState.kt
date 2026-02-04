package com.example.operation_android.ui.session

data class SessionUiState(
    val startTimeMillis: Long = System.currentTimeMillis(),
    val elapsedSeconds: Long = 0L
)
