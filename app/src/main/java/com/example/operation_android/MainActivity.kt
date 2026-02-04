package com.example.operation_android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.operation_android.analytics.AnalyticsLogger
import com.example.operation_android.ui.navigation.AppNavigation
import com.google.firebase.analytics.FirebaseAnalytics

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val firebaseAnalytics = FirebaseAnalytics.getInstance(this)
        val analyticsLogger = AnalyticsLogger(firebaseAnalytics)

        setContent {
            MaterialTheme {
                Surface(modifier = Modifier) {
                    AppNavigation(analyticsLogger)
                }
            }
        }
    }
}
