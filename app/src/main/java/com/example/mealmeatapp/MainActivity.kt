package com.example.mealmeatapp


import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.example.mealmeatapp.controller.NavigationController
import com.example.mealmeatapp.view.home.EatSmartLiveBetterScreen
import com.example.mealmeatapp.view.home.MealmateScreen
import com.example.mealmeatapp.view.onboarding.PersonalProgramScreen

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                // Gọi NavigationController trực tiếp trong MainActivity
                NavigationController()

            }
        }
    }
}
