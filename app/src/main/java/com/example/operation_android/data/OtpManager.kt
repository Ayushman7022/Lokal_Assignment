package com.example.operation_android.data

import kotlin.random.Random

class OtpManager {

    private data class OtpData(
        val otp: String,
        val createdAt: Long,
        var attemptsLeft: Int
    )

    private val otpStore = mutableMapOf<String, OtpData>()

    private val OTP_EXPIRY_MS = 60_000L
    private val MAX_ATTEMPTS = 3

    fun generateOtp(email: String): String {
        val otp = Random.nextInt(100000, 999999).toString()

        otpStore[email] = OtpData(
            otp = otp,
            createdAt = System.currentTimeMillis(),
            attemptsLeft = MAX_ATTEMPTS
        )

        // Assignment requirement: show OTP in logcat
        android.util.Log.d("OTP_DEBUG", "OTP for $email = $otp")

        return otp
    }

    fun verifyOtp(email: String, inputOtp: String): OtpResult {
        val data = otpStore[email] ?: return OtpResult.NoOtp

        if (System.currentTimeMillis() - data.createdAt > OTP_EXPIRY_MS) {
            otpStore.remove(email)
            return OtpResult.Expired
        }

        if (data.attemptsLeft <= 0) {
            otpStore.remove(email)
            return OtpResult.AttemptsExceeded
        }

        return if (data.otp == inputOtp) {
            otpStore.remove(email)
            OtpResult.Success
        } else {
            data.attemptsLeft--
            OtpResult.Invalid(data.attemptsLeft)
        }
    }
}

sealed class OtpResult {
    object Success : OtpResult()
    object Expired : OtpResult()
    object AttemptsExceeded : OtpResult()
    object NoOtp : OtpResult()
    data class Invalid(val attemptsLeft: Int) : OtpResult()
}
