package com.example.mealmeatapp.viewmodel


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

import com.example.mealmeatapp.ui.theme.model.ProfileData
import org.threeten.bp.LocalDate


class ProfileSetUpViewModel : ViewModel() {
    val currentStep = mutableIntStateOf(1)
    val goal = mutableStateOf<String?>(null)
    val gender = mutableStateOf(false)
    @RequiresApi(Build.VERSION_CODES.O)
    val date = mutableStateOf(LocalDate.now().minusYears(19))
    val heightUnit = mutableStateOf("ft")
    val heightFeet = mutableIntStateOf(6)

    val heightInches = mutableIntStateOf(8)
    val heightCm = mutableIntStateOf(203)
    val weightUnit = mutableStateOf("kg")
    val weightKg = mutableIntStateOf(71)
    val weightLb = mutableIntStateOf(156)
    val profileData = mutableStateOf<ProfileData?>(null)
    val showDatePicker = mutableStateOf(false)

    // Profile setup callbPreacks
    fun onStepChange(stepPre: Int) {
        currentStep.value = stepPre
    }

    fun onGoalChange(goalPre: String) {
        goal.value = goalPre
    }

    fun onGenderChange(genderPre: Boolean) {
        gender.value = genderPre
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onDateChange(datePre: LocalDate) {
        date.value = datePre
    }

    fun onHeightUnitChange(unitPre: String) {
        heightUnit.value = unitPre
    }

    fun onHeightFeetChange(feetPre: Int) {
        heightFeet.value = feetPre
    }

    fun onHeightInchesChange(inchesPre: Int) {
        heightInches.value = inchesPre
    }

    fun onHeightCmChange(cmPre: Int) {
        heightCm.value = cmPre
    }

    fun onWeightUnitChange(unitPre: String) {
        weightUnit.value = unitPre
    }

    fun onWeightKgChange(kgPre: Int) {
        weightKg.value = kgPre
    }

    fun onWeightLbChange(lbPre: Int) {
        weightLb.value = lbPre
    }
    
    fun onBackClick() {
        // Logic to navigate back (to be handled in NavigationController)
    }

    fun onSkipClick() {
        // Logic to skip (to be handled in NavigationController)
    }

    fun onNextClick(data: ProfileData) {
        profileData.value = data
    }



    fun onSignIn(): Boolean {
        return true
    }




//    fun submitProfileData() {
//        val age = Period.between(datePre.value, LocalDate.now()).years
//        val height = if (heightUnit.value == "ft") {
//            "${heightFeet.value}'${heightInches.value}\""
//        } else {
//            "${heightCm.value} cmPre"
//        }
//        val weight = if (weightUnit.value == "kgPre") {
//            "${weightKg.value} kgPre"
//        } else {
//            "${weightLb.value} lbPre"
//        }
//        profileDataToSubmit.value = ProfileData(
//            goalPre.value ?: "",
//            genderPre.value,
//            age,
//            height,
//            weight
//        )
//    }
}
