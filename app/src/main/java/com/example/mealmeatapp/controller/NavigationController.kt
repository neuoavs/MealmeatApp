package com.example.mealmeatapp.controller


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContent
import androidx.compose.runtime.*
import com.example.mealmeatapp.R
import com.example.mealmeatapp.model.OnboardingPage
import com.example.mealmeatapp.model.ProfileCalculations
import com.example.mealmeatapp.model.ProfileData
import com.example.mealmeatapp.view.auth.LoginScreen
import com.example.mealmeatapp.view.auth.SignUpScreen
import com.example.mealmeatapp.view.home.EatSmartLiveBetterScreen
import com.example.mealmeatapp.view.home.MealmateScreen
import com.example.mealmeatapp.view.onboarding.*
import com.example.mealplan.controller.Screen
import com.example.mealplan.view.onboarding.CheckmarkScreen
import com.example.myproject.view.onboarding.ProfileSetupScreen
import kotlinx.coroutines.delay
import java.time.LocalDate

// Utility functions for validation
fun isValidEmail(email: String): Boolean {
    return email.isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

fun isValidPassword(password: String): Boolean {
    // Example: Password must be at least 8 characters long
    return password.length >= 8
}
// Define the Screen sealed class to include all possible screens

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavigationController() {
    // State for current screen
    var currentScreen by remember { mutableStateOf<Screen>(Screen.EatSmartLiveBetter) }
    var profileData by remember { mutableStateOf<ProfileData?>(null) }

    // Onboarding state
    val onboardingPages = listOf(
        OnboardingPage(R.drawable.amico1, "KNOW WHAT TO EAT", "Gain insight in your nutritional habits with detailed statistics"),
        OnboardingPage(R.drawable.rafiki2, "TRACK YOUR DIET", "We will help you lose weight, stay fit or build muscle"),
        OnboardingPage(R.drawable.pana, "LIVE HEALTHY & GREAT", "Let's start this journey and live healthy together"),
        OnboardingPage(R.drawable.rafiki1, "LET'S COMPLETE YOUR PROFILE", "We use your information to serve the estimates of your nutrients and follow your progress")
    )
    var currentOnboardingPage by remember { mutableStateOf(0) }

    // Profile setup state
    var currentProfileStep by remember { mutableStateOf(1) }
    var selectedGoal by remember { mutableStateOf<String?>(null) }
    var selectedGender by remember { mutableStateOf<String?>(null) }
    var selectedDate by remember { mutableStateOf(LocalDate.now().minusYears(19)) }
    var heightUnit by remember { mutableStateOf("ft") }
    var heightFeet by remember { mutableStateOf(6) }
    var heightInches by remember { mutableStateOf(8) }
    var heightCm by remember { mutableStateOf(203) }
    var weightUnit by remember { mutableStateOf("kg") }
    var weightKg by remember { mutableStateOf(71) }
    var weightLb by remember { mutableStateOf(156) }
    var selectedProgress by remember { mutableStateOf<String?>(null) }

    // Auto transition from EatSmartLiveBetter to Mealmate
    LaunchedEffect(currentScreen) {
        if (currentScreen == Screen.EatSmartLiveBetter) {
            delay(3000L)
            currentScreen = Screen.Mealmate
        }
    }

    // Auto transition for PreparingPlan and Checkmark screens
    LaunchedEffect(currentScreen) {
        when (currentScreen) {
            Screen.PreparingPlan -> {
                delay(3000L)
                currentScreen = Screen.Checkmark
            }
            Screen.Checkmark -> {
                delay(1000L)
                currentScreen = Screen.PersonalProgram
            }
            else -> {}
        }
    }

    AnimatedContent(targetState = currentScreen) { screen ->
        when (screen) {
            Screen.EatSmartLiveBetter -> EatSmartLiveBetterScreen(
                onLogoClick = { currentScreen = Screen.Mealmate }
            )
            Screen.Mealmate -> MealmateScreen(
                onLoginClick = { currentScreen = Screen.Login }
            )
            Screen.Login -> LoginScreen(
                onSignUpClick = { currentScreen = Screen.SignUp },
                onLoginClick = { email, password, rememberMe ->
                    // TODO: Thêm logic xác thực ở đây
                    // Ví dụ: Kiểm tra định dạng email và mật khẩu
                    if (isValidEmail(email) && isValidPassword(password)) {
                        // Nếu xác thực thành công, chuyển đến màn hình Onboarding
                        println("Login clicked: Email=$email, Password=$password, RememberMe=$rememberMe")
                        currentScreen = Screen.Onboarding
                    } else {
                        // Nếu xác thực không thành công, hiển thị thông báo lỗi
                        println("Invalid email or password")
                    }
                }
            )
            Screen.SignUp -> SignUpScreen(
                onLoginClick = { currentScreen = Screen.Login }
            )
            Screen.Onboarding -> OnboardingScreen(
                pages = onboardingPages,
                currentPage = currentOnboardingPage,
                onPageChange = { pageIndex -> currentOnboardingPage = pageIndex },
                onNextClick = { currentScreen = Screen.ProfileSetup },
                onSkipClick = { currentScreen = Screen.ProfileSetup }
            )
            Screen.ProfileSetup -> ProfileSetupScreen(
                currentStep = currentProfileStep,
                selectedGoal = selectedGoal,
                selectedGender = selectedGender,
                selectedDate = selectedDate,
                heightUnit = heightUnit,
                heightFeet = heightFeet,
                heightInches = heightInches,
                heightCm = heightCm,
                weightUnit = weightUnit,
                weightKg = weightKg,
                weightLb = weightLb,
                selectedProgress = selectedProgress,
                onStepChange = { step -> currentProfileStep = step },
                onGoalChange = { goal -> selectedGoal = goal },
                onGenderChange = { gender -> selectedGender = gender },
                onDateChange = { date -> selectedDate = date },
                onHeightUnitChange = { unit -> heightUnit = unit },
                onHeightFeetChange = { feet -> heightFeet = feet },
                onHeightInchesChange = { inches -> heightInches = inches },
                onHeightCmChange = { cm -> heightCm = cm },
                onWeightUnitChange = { unit -> weightUnit = unit },
                onWeightKgChange = { kg -> weightKg = kg },
                onWeightLbChange = { lb -> weightLb = lb },
                onProgressChange = { progress -> selectedProgress = progress },
                onBackClick = { currentScreen = Screen.Onboarding },
                onSkipClick = { currentScreen = Screen.Completion },
                onNextClick = { data ->
                    profileData = data
                    currentScreen = Screen.Completion
                }
            )
            Screen.Completion -> CompletionScreen(
                onNextClick = { currentScreen = Screen.PreparingPlan }
            )
            Screen.PreparingPlan -> PreparingPlanScreen()
            Screen.Checkmark -> CheckmarkScreen()
            Screen.PersonalProgram -> {
                val defaultProfileData = ProfileData(
                    goal = "Stay healthy",
                    gender = "Male",
                    age = 30,
                    height = "170 cm",
                    weight = "70 kg",
                    progress = "Steady"
                )
                val nutritionPlan = ProfileCalculations.calculateNutritionPlan(profileData ?: defaultProfileData)
                PersonalProgramScreen(
                    nutritionPlan = nutritionPlan,
                    weeks = 12,
                    onExploreClick = { currentScreen = Screen.Mealmate }
                )
            }
        }
    }
}