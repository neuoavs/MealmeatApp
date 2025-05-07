package com.example.mealmeatapp.controller

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.mealmeatapp.ui.theme.model.ProfileData
import java.time.LocalDate

class AuthViewModel : ViewModel() {
    val email = mutableStateOf("")
    val password = mutableStateOf("")
    val passwordVisible = mutableStateOf(false)
    val userCredentials = mutableStateOf(UserCredentials())
    val confirmPassword = mutableStateOf("")
    val confirmPasswordVisible = mutableStateOf(false)

    // Profile setup states
    val currentProfileStep = mutableStateOf(1)
    val selectedGoal = mutableStateOf<String?>(null)
    val selectedGender = mutableStateOf<String?>(null)
    @RequiresApi(Build.VERSION_CODES.O)
    val selectedDate = mutableStateOf(LocalDate.now().minusYears(19))
    val heightUnit = mutableStateOf("ft")
    val heightFeet = mutableStateOf(6)
    val heightInches = mutableStateOf(8)
    val heightCm = mutableStateOf(203)
    val weightUnit = mutableStateOf("kg")
    val weightKg = mutableStateOf(71)
    val weightLb = mutableStateOf(156)
    val selectedProgress = mutableStateOf<String?>(null)
    val profileData = mutableStateOf<ProfileData?>(null)

    // Profile setup callbacks
    fun onStepChange(step: Int) {
        currentProfileStep.value = step
    }

    fun onGoalChange(goal: String) {
        selectedGoal.value = goal
    }

    fun onGenderChange(gender: String) {
        selectedGender.value = gender
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onDateChange(date: LocalDate) {
        selectedDate.value = date
    }

    fun onHeightUnitChange(unit: String) {
        heightUnit.value = unit
    }

    fun onHeightFeetChange(feet: Int) {
        heightFeet.value = feet
    }

    fun onHeightInchesChange(inches: Int) {
        heightInches.value = inches
    }

    fun onHeightCmChange(cm: Int) {
        heightCm.value = cm
    }

    fun onWeightUnitChange(unit: String) {
        weightUnit.value = unit
    }

    fun onWeightKgChange(kg: Int) {
        weightKg.value = kg
    }

    fun onWeightLbChange(lb: Int) {
        weightLb.value = lb
    }

    fun onProgressChange(progress: String) {
        selectedProgress.value = progress
    }

    fun onBackClick() {
        // Logic to navigate back (to be handled in NavigationController)
    }

    fun onSkipClick() {
        // Logic to skip (to be handled in NavigationController)
    }

    fun onNextClick(data: ProfileData) {
        profileData.value = data
        // Logic to navigate to next screen (to be handled in NavigationController)
    }



    fun onSignIn(): Boolean {
        return true // Always allow navigation for frontend-only
    }

    fun togglePasswordVisibility() {
        passwordVisible.value = !passwordVisible.value
    }

    fun toggleConfirmPasswordVisibility() {
        confirmPasswordVisible.value = !confirmPasswordVisible.value
    }

    fun onSignUp() {
        // No-op for frontend-only, just updates state
        userCredentials.value = userCredentials.value.copy(
            email = email.value,
            password = password.value
        )
    }
    fun logout() {
        userCredentials.value = UserCredentials()
        email.value = ""
        password.value = ""
        confirmPassword.value = ""
        profileData.value = null
    }

    fun onForgotPassword() {
        // No-op for frontend-only
    }
}

data class UserCredentials(
    val name: String = "",
    val email: String = "",
    val password: String = ""
)
