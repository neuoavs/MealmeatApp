package com.example.mealmeatapp.view

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mealmeatapp.ui.theme.MealtimeAppTheme
import com.example.mealmeatapp.viewmodel.ProfileSetUpViewModel


@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ProfileSetupScreen(
    navController: NavController,
    profileSetUpViewModel: ProfileSetUpViewModel
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF9E6))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            HeaderProfile(
                navController = navController,
                profileSetUpViewModel = profileSetUpViewModel
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            when (profileSetUpViewModel.currentStep.intValue) {
                1 -> {
                    StepOne(
                        profileSetUpViewModel = profileSetUpViewModel
                    )
                }
                2 -> {
                    StepTwo(
                        profileSetUpViewModel = profileSetUpViewModel
                    )
                }
                3 -> {
                    StepThree(
                        profileSetUpViewModel = profileSetUpViewModel
                    )
                }
                4 -> {
                    StepFour(
                        profileSetUpViewModel = profileSetUpViewModel
                    )
                }
                5 -> {
                    StepFive(
                        profileSetUpViewModel = profileSetUpViewModel
                    )
                }
            }
        }

        ProfileDialog(
            profileSetUpViewModel = profileSetUpViewModel,
        )
        // Footer
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            NextButtonProfile(
                profileSetUpViewModel = profileSetUpViewModel
            )
            Spacer(modifier = Modifier.height(16.dp))
            StepCircle(
                profileSetUpViewModel = profileSetUpViewModel
            )
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun ProfileSetupPreview() {
    MealtimeAppTheme {
        ProfileSetupScreen(
            navController = rememberNavController() ,
            profileSetUpViewModel = ProfileSetUpViewModel()
        )
    }
}