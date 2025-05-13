package com.example.mealmeatapp.viewmodel


import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.mealmeatapp.model.ProfileData
import java.time.LocalDate
import java.time.Period


class ProfileSetUpViewModel : ViewModel() {
    // Profile setup states
    val currentStep = mutableIntStateOf(1)
    val selectedGoal = mutableStateOf<String?>(null)
    val selectedGender = mutableStateOf(true) // true for male, false for female
    @RequiresApi(Build.VERSION_CODES.O)
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
    @RequiresApi(Build.VERSION_CODES.O)
    val age = mutableIntStateOf(calculateAge(selectedDate.value))

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

    @RequiresApi(Build.VERSION_CODES.O)
    private fun commitProfileData(
        profileViewModel: ProfileViewModel,
        homeViewModel: HomeViewModel
    ) {
        val profileData = ProfileData(
            isDiet = if(selectedGoal.value == "Less weight") true else false,
            gender = selectedGender.value,
            age = age.intValue,
            heightCm = heightCm.intValue,
            heightFeetInches = heightInFeetInches.value,
            heightUnit = heightUnit.value,
            weight = getWeighValue(),
            weightUnit = weightUnit.value
        )
        profileDataToSubmit.value = profileData
        profileViewModel.mergeProfile(profileData)
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
        age.value = calculateAge(date)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun calculateAge(dateOfBirth: LocalDate): Int {
        return Period.between(dateOfBirth, LocalDate.now()).years
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

    fun onSkipClick(
        navController: NavController
    ) {
        navController.navigate("home")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onNextClick(
        profileViewModel: ProfileViewModel,
        homeViewModel: HomeViewModel
    ) {
        when (currentStep.intValue) {
            1 -> if (selectedGoal.value != null) ++currentStep.value
            2 -> ++currentStep.value
            3 -> ++currentStep.value
            4 -> ++currentStep.value
            5 -> {
                // Tạo ProfileData để hiển thị trong Dialog
                commitProfileData(profileViewModel, homeViewModel)
                showSummaryDialog.value = true
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onConfirmClick(
        navController: NavController,
        profileViewModel: ProfileViewModel,
        homeViewModel: HomeViewModel
    ) {
        showSummaryDialog.value = false
        commitProfileData(
            profileViewModel = profileViewModel,
            homeViewModel = homeViewModel
        )


        homeViewModel.fetchRandomRecipes(profileViewModel)
        Toast.makeText(navController.context, "Profile saved successfully", Toast.LENGTH_SHORT).show()
        if (profileViewModel.isUpdateProfile.value) {
            navController.navigate("profile")
            profileViewModel.isUpdateProfile.value = false
        } else {
            navController.navigate("home")
        }
    }

    fun getHeightValueString(): String {
        return if (heightUnit.value == "ft") {
            "${heightFeet.intValue}'${heightInches.intValue}"
        } else {
            "${heightCm.intValue} cm"
        }
    }

    fun getWeightValueString(): String {
        return if (weightUnit.value == "kg") {
            "${weightKg.intValue} kg"
        } else {
            "${weightLb.intValue} lb"
        }
    }

    fun getGenderValueString(): String {
        return if (selectedGender.value) {
            "Male"
        } else {
            "Female"
        }
    }

    fun getWeighValue(): Int {
        return if (weightUnit.value == "kg") {
            weightKg.intValue
        } else {
            weightLb.intValue
        }
    }
}
