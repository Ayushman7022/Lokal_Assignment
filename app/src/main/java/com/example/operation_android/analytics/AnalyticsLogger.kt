package com.example.operation_android.analytics

import android.os.Bundle
import android.util.Log
import com.google.firebase.analytics.FirebaseAnalytics

class AnalyticsLogger(
    private val firebaseAnalytics: FirebaseAnalytics
) {

    fun logOtpGenerated(email: String) {
        Log.d("Analytics", "OTP generated for $email")
        firebaseAnalytics.logEvent("otp_generated", Bundle().apply {
            putString("email_hash", email.hashCode().toString())
        })
    }

    fun logOtpSuccess(email: String) {
        Log.d("Analytics", "OTP verified for $email")
        firebaseAnalytics.logEvent("otp_success", Bundle().apply {
            putString("email_hash", email.hashCode().toString())
        })
    }

    fun logOtpFailure(email: String) {
        Log.d("Analytics", "OTP failed for $email")
        firebaseAnalytics.logEvent("otp_failure", Bundle().apply {
            putString("email_hash", email.hashCode().toString())
        })
    }

    fun logLogout() {
        Log.d("Analytics", "User logged out")
        firebaseAnalytics.logEvent("logout", null)
    }
}
