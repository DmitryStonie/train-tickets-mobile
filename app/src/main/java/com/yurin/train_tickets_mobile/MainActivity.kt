package com.yurin.train_tickets_mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.yurin.train_tickets_mobile.ui.screen.MainScreen
import com.yurin.train_tickets_mobile.ui.theme.Train_tickets_mobileTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Train_tickets_mobileTheme {
                MainScreen()
            }
        }
    }
}