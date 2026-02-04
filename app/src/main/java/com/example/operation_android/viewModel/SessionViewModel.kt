package com.example.operation_android.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.operation_android.ui.session.SessionUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class SessionViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(SessionUiState())
    val uiState: StateFlow<SessionUiState> = _uiState

    init {
        startTimer()
    }

    private fun startTimer() {
        viewModelScope.launch {
            while (isActive) {
                val elapsed =
                    (System.currentTimeMillis() - _uiState.value.startTimeMillis) / 1000

                _uiState.value = _uiState.value.copy(
                    elapsedSeconds = elapsed
                )

                delay(1000)
            }
        }
    }
}
