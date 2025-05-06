package com.example.mealplan.controller


sealed class Screen {
    object EatSmartLiveBetter : Screen()
    object Mealmate : Screen()
    object Login : Screen()
    object SignUp : Screen()
    object Onboarding : Screen()
    object ProfileSetup : Screen()
    object Completion : Screen()
    object PreparingPlan : Screen()
    object Checkmark : Screen()
    object PersonalProgram : Screen()
}
