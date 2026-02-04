package com.example.operation_android.viewModel

import androidx.lifecycle.ViewModel
import com.example.operation_android.data.OtpManager
import com.example.operation_android.data.OtpResult
import com.example.operation_android.ui.auth.AuthUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class AuthViewModel(
    private val otpManager: OtpManager = OtpManager()
) : ViewModel() {

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState

    /* ---------------- EMAIL ---------------- */

    fun onEmailChanged(email: String) {
        _uiState.update {
            it.copy(
                email = email,
                message = ""
            )
        }
    }

    /* ---------------- OTP INPUT ---------------- */

    fun onOtpChanged(otp: String) {
        _uiState.update {
            it.copy(
                otp = otp,
                message = ""
            )
        }
    }

    /* ---------------- SEND OTP ---------------- */

    fun sendOtp(): Boolean {
        val email = _uiState.value.email

        if (email.isBlank()) {
            _uiState.update {
                it.copy(message = "Please enter email")
            }
            return false
        }

        otpManager.generateOtp(email)

        _uiState.update {
            it.copy(
                isOtpSent = true,
                message = "OTP sent to $email (check Logcat)"
            )
        }
        return true
    }

    /* ---------------- VERIFY OTP ---------------- */

    fun verifyOtp(): Boolean {
        val state = _uiState.value

        if (state.otp.length != 6) {
            _uiState.update {
                it.copy(message = "Enter valid 6-digit OTP")
            }
            return false
        }

        return when (val result =
            otpManager.verifyOtp(state.email, state.otp)) {

            is OtpResult.Success -> {
                _uiState.update {
                    it.copy(
                        isLoggedIn = true,
                        message = "Login successful"
                    )
                }
                true
            }

            is OtpResult.Invalid -> {
                _uiState.update {
                    it.copy(
                        message = "Invalid OTP. ${result.attemptsLeft} attempts left"
                    )
                }
                false
            }

            OtpResult.Expired -> {
                _uiState.update {
                    it.copy(message = "OTP expired. Request a new one")
                }
                false
            }

            OtpResult.AttemptsExceeded -> {
                _uiState.update {
                    it.copy(message = "Max attempts reached. Request new OTP")
                }
                false
            }

            OtpResult.NoOtp -> {
                _uiState.update {
                    it.copy(message = "Please request OTP first")
                }
                false
            }
        }
    }

    /* ---------------- RESET (LOGOUT) ---------------- */

    fun reset() {
        _uiState.value = AuthUiState()
    }
}
