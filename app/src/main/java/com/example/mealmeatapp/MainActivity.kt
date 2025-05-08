package com.example.mealmeatapp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mealmateapp.ui.theme.view.FavoriteScreen
import com.example.mealmateapp.ui.theme.view.FoodDetailScreen
import com.example.mealmeatapp.controller.AuthViewModel
import com.example.mealmeatapp.ui.theme.controller.HomeViewModel
import com.example.mealmeatapp.ui.theme.MealtimeAppTheme
import com.example.mealmeatapp.ui.theme.view.ForgotPasswordScreen
import com.example.mealmeatapp.ui.theme.view.HomeScreen
import com.example.mealmeatapp.ui.theme.view.MealPlannerScreen
import com.example.mealmeatapp.ui.theme.view.MealtimeScreen
import com.example.mealmeatapp.ui.theme.view.ProfileSetupScreen
import com.example.mealmeatapp.ui.theme.view.SettingScreen
import com.example.mealmeatapp.ui.theme.view.SignInScreen
import com.example.mealmeatapp.ui.theme.view.SignUpScreen


class MainActivity : ComponentActivity() {
    private val authViewModel: AuthViewModel by viewModels()
    private val homeViewModel: HomeViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MealtimeAppTheme {
                val navController = rememberNavController()
                MainScreen(navController, authViewModel, homeViewModel)
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    homeViewModel: HomeViewModel
) {
    Scaffold { innerPadding ->
        AppNavigation(
            navController = navController,
            authViewModel = authViewModel,
            homeViewModel = homeViewModel,
            modifier = Modifier.padding(innerPadding)
        )
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigation(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    homeViewModel: HomeViewModel,
    modifier: Modifier = Modifier
) {
    NavHost(navController = navController, startDestination = "mealtime", modifier = modifier) {
        composable("mealtime") {
            MealtimeScreen(
                onSignUpClick = { navController.navigate("signup") },
                onSignInClick = { navController.navigate("signin") }
            )
        }
        composable("signup") {
            SignUpScreen(
                viewModel = authViewModel,
                navController = navController,
                onSignUpSuccess = {
                    navController.navigate("signin") {
                        popUpTo("signup") { inclusive = true }
                    }
                }
            )
        }
        composable("signin") {
            SignInScreen(
                viewModel = authViewModel,
                navController = navController,
                onForgotPasswordClick = {
                    navController.navigate("forgotpassword")
                },
                onSignInSuccess = {
                    if (authViewModel.onSignIn()) {
                        navController.navigate("profilesetup") {
                            popUpTo("signin") { inclusive = true }
                        }
                    }
                }

            )
        }
        composable("forgotpassword") {
            ForgotPasswordScreen(
                viewModel = authViewModel,
                navController = navController
            )
        }
        composable("profilesetup") {
            ProfileSetupScreen(
                currentStep = authViewModel.currentProfileStep.value,
                selectedGoal = authViewModel.selectedGoal.value,
                selectedGender = authViewModel.selectedGender.value,
                selectedDate = authViewModel.selectedDate.value,
                heightUnit = authViewModel.heightUnit.value,
                heightFeet = authViewModel.heightFeet.value,
                heightInches = authViewModel.heightInches.value,
                heightCm = authViewModel.heightCm.value,
                weightUnit = authViewModel.weightUnit.value,
                weightKg = authViewModel.weightKg.value,
                weightLb = authViewModel.weightLb.value,
                selectedProgress = authViewModel.selectedProgress.value,
                onStepChange = { step -> authViewModel.onStepChange(step) },
                onGoalChange = { goal -> authViewModel.onGoalChange(goal) },
                onGenderChange = { gender -> authViewModel.onGenderChange(gender) },
                onDateChange = { date -> authViewModel.onDateChange(date) },
                onHeightUnitChange = { unit -> authViewModel.onHeightUnitChange(unit) },
                onHeightFeetChange = { feet -> authViewModel.onHeightFeetChange(feet) },
                onHeightInchesChange = { inches -> authViewModel.onHeightInchesChange(inches) },
                onHeightCmChange = { cm -> authViewModel.onHeightCmChange(cm) },
                onWeightUnitChange = { unit -> authViewModel.onWeightUnitChange(unit) },
                onWeightKgChange = { kg -> authViewModel.onWeightKgChange(kg) },
                onWeightLbChange = { lb -> authViewModel.onWeightLbChange(lb) },
                onProgressChange = { progress -> authViewModel.onProgressChange(progress) },
                onBackClick = {
                    authViewModel.onBackClick()
                    navController.navigate("signin") {
                        popUpTo("profilesetup") { inclusive = true }
                    }
                },
                onSkipClick = {
                    authViewModel.onSkipClick()
                    navController.navigate("home") {
                        popUpTo("profilesetup") { inclusive = true }
                    }
                },
                onNextClick = { data ->
                    authViewModel.onNextClick(data)
                    navController.navigate("home") {
                        popUpTo("profilesetup") { inclusive = true }
                    }
                }
            )
        }
        composable("home") {
            HomeScreen(viewModel = homeViewModel, navController = navController)
        }
        composable("favorite") {
            FavoriteScreen(viewModel = homeViewModel, navController = navController)
        }
        composable("planner") {
            MealPlannerScreen(viewModel = homeViewModel, navController = navController)
        }
        composable(
            route = "foodDetail/{mealId}",
            arguments = listOf(
                navArgument("mealId") { type = androidx.navigation.NavType.IntType }
            )
        ) { backStackEntry ->
            val mealId = backStackEntry.arguments?.getInt("mealId") ?: 0
            FoodDetailScreen(
                navController = navController,
                mealId = mealId
            )
        }
        composable("setting") {
            SettingScreen(
                navController = navController,
                authViewModel = authViewModel,
                homeViewModel = homeViewModel
            )
        }
    }
}