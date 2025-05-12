package com.example.mealmeatapp.view

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.mealmeatapp.R
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mealmeatapp.viewmodel.ProfileSetUpViewModel
import com.example.mealmeatapp.view.component.*
import com.example.mealmeatapp.viewmodel.HomeViewModel
import com.example.mealmeatapp.viewmodel.ProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ProfileSetupScreen(
    navController: NavController,
    profileSetUpViewModel: ProfileSetUpViewModel,
    profileViewModel: ProfileViewModel,
    homeViewModel: HomeViewModel
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                colorResource(id = R.color.light_cream)
            )
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
            navController = navController,
            profileSetUpViewModel = profileSetUpViewModel,
            profileViewModel = profileViewModel,
            homeViewModel = homeViewModel
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
                profileSetUpViewModel = profileSetUpViewModel,
                profileViewModel = profileViewModel,
                homeViewModel = homeViewModel
            )
            Spacer(modifier = Modifier.height(16.dp))
            StepCircle(
                profileSetUpViewModel = profileSetUpViewModel
            )
        }
    }
}


