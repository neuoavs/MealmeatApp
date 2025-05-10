package com.example.mealmeatapp.viewmodel


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController

import com.example.mealmeatapp.ui.theme.model.ProfileData
import org.threeten.bp.LocalDate
import org.threeten.bp.Period


class ProfileSetUpViewModel : ViewModel() {
    // Profile setup states
    val currentStep = mutableIntStateOf(1)
    val selectedGoal = mutableStateOf<String?>(null)
    val selectedGender = mutableStateOf(true) // true for male, false for female
    val selectedDate = mutableStateOf(LocalDate.now().minusYears(19))
    val heightUnit = mutableStateOf("ft")
    val heightFeet = mutableIntStateOf(6)
    val heightInches = mutableIntStateOf(8)
    val heightCm = mutableIntStateOf(203)
    val weightUnit = mutableStateOf("kg")
    val weightKg = mutableIntStateOf(71)
    val weightLb = mutableIntStateOf(156)
    val profileDataToSubmit = mutableStateOf<ProfileData?>(null)
    val showDatePicker = mutableStateOf(false)
    var showSummaryDialog = mutableStateOf(false)
    val age = mutableIntStateOf(Period.between(selectedDate.value, LocalDate.now()).years)

    val heightInCm = mutableIntStateOf(
        if (heightUnit.value == "ft") {
            (heightFeet.intValue * 30.48 + heightInches.intValue * 2.54).toInt()
        } else {
            heightCm.intValue
        }
    )

    val heightInFeetInches = mutableStateOf(
        if (heightUnit.value == "cm") {
            val totalInches = (heightCm.intValue / 2.54).toInt()
            val feet = totalInches / 12
            val inches = totalInches % 12
            feet to inches
        } else {
            heightFeet.intValue to heightInches.intValue
        }
    )

    val weightInKg = mutableIntStateOf(
        if (weightUnit.value == "kg") {
            weightKg.intValue
        } else {
            (weightLb.intValue / 2.2).toInt()
        }
    )

    val weightInLb = mutableIntStateOf(
        if (weightUnit.value == "lb") {
            weightLb.intValue
        } else {
            (weightKg.intValue * 2.2).toInt()
        }
    )

    fun commitProfileData() {
        profileDataToSubmit.value = ProfileData(
            goal = selectedGoal.value ?: "",
            gender = selectedGender.value,
            age = age.intValue,
            heightCm = heightCm.intValue,
            heightFeetInches = heightInFeetInches.value,
            heightUnit = heightUnit.value,
            weight = getWeighValue(),
            weightUnit = weightUnit.value
        )
    }

    // Profile setup callbacks
    fun onStepChange(step: Int) {
        currentStep.value = step
    }

    fun onGoalChange(goal: String) {
        selectedGoal.value = goal
    }

    fun onGenderChange(gender: Boolean) {
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

    fun onBackClick(
        navController: NavController
    ) {
        navController.navigate("sign_in")
    }

    fun onSkipClick() {
        // Logic to skip (to be handled in NavigationController)
    }

    fun onNextClick(data: ProfileData) {
        profileDataToSubmit.value = data
        // Logic to navigate to next screen (to be handled in NavigationController)
    }

    fun onConfirmClick() {

    }

    fun getHeighValueString(): String {
        if (heightUnit.value == "ft") {
            return "${heightFeet.intValue}'${heightInches.intValue}"
        } else {
            return "${heightCm.intValue} cm"
        }
    }

    fun getGenderValueString(): String {
        if (selectedGender.value) {
            return "Male"
        } else {
            return "Female"
        }
    }

    fun getWeighValue(): Int {
        if (weightUnit.value == "kg") {
            return weightKg.intValue
        } else {
            return weightLb.intValue
        }
    }
}
