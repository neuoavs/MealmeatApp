package com.example.mealmeatapp.viewmodel


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

import com.example.mealmeatapp.ui.theme.model.ProfileData
import org.threeten.bp.LocalDate
import org.threeten.bp.Period


class ProfileSetUpViewModel : ViewModel() {
    // Profile setup states
    val currentStep = mutableIntStateOf(1)
    val selectedGoal = mutableStateOf<String?>(null)
    val selectedGender = mutableStateOf<String?>(null)
    val selectedDate = mutableStateOf(LocalDate.now().minusYears(19))
    val heightUnit = mutableStateOf("ft")
    val heightFeet = mutableIntStateOf(6)
    val heightInches = mutableIntStateOf(8)
    val heightCm = mutableIntStateOf(203)
    val weightUnit = mutableStateOf("kg")
    val weightKg = mutableIntStateOf(71)
    val weightLb = mutableIntStateOf(156)
    val selectedProgress = mutableStateOf<String?>(null)
    val profileDataToSubmit = mutableStateOf<ProfileData?>(null)
    val showDatePicker = mutableStateOf(false)
    var showSummaryDialog = mutableStateOf(false)
    val age = mutableIntStateOf(Period.between(selectedDate.value, LocalDate.now()).years)

    // Profile setup callbacks
    fun onStepChange(step: Int) {
        currentStep.value = step
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


    fun onForgotPassword() {
        // No-op for frontend-only
    }
}
