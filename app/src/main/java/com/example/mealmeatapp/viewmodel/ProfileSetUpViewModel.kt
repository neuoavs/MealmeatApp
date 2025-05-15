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
    val showSummaryDialog = mutableStateOf(false)
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

    // Hàm reset trạng thái của ProfileSetUpViewModel
    @RequiresApi(Build.VERSION_CODES.O)
    fun resetState() {
        currentStep.intValue = 1
        selectedGoal.value = null
        selectedGender.value = true
        selectedDate.value = LocalDate.now().minusYears(19)
        heightUnit.value = "ft"
        heightFeet.intValue = 6
        heightInches.intValue = 8
        heightCm.intValue = 203
        weightUnit.value = "kg"
        weightKg.intValue = 71
        weightLb.intValue = 156
        profileDataToSubmit.value = null
        showDatePicker.value = false
        showSummaryDialog.value = false
        age.intValue = calculateAge(selectedDate.value)
        updateDerivedStates()
    }

    // Cập nhật các trạng thái phụ thuộc
    @RequiresApi(Build.VERSION_CODES.O)
    private fun updateDerivedStates() {
        heightInCm.intValue = if (heightUnit.value == "ft") {
            (heightFeet.intValue * 30.48 + heightInches.intValue * 2.54).toInt()
        } else {
            heightCm.intValue
        }

        heightInFeetInches.value = if (heightUnit.value == "cm") {
            val totalInches = (heightCm.intValue / 2.54).toInt()
            val feet = totalInches / 12
            val inches = totalInches % 12
            feet to inches
        } else {
            heightFeet.intValue to heightInches.intValue
        }

        weightInKg.intValue = if (weightUnit.value == "kg") {
            weightKg.intValue
        } else {
            (weightLb.intValue / 2.2).toInt()
        }

        weightInLb.intValue = if (weightUnit.value == "lb") {
            weightLb.intValue
        } else {
            (weightKg.intValue * 2.2).toInt()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun commitProfileData(
        profileViewModel: ProfileViewModel,
        homeViewModel: HomeViewModel
    ) {
        // Cập nhật các trạng thái phụ thuộc trước khi tạo ProfileData
        updateDerivedStates()

        val profileData = ProfileData(
            isDiet = selectedGoal.value == "Less weight",
            gender = selectedGender.value,
            age = age.intValue,
            heightCm = if (heightUnit.value == "ft") {
                (heightFeet.intValue * 30.48 + heightInches.intValue * 2.54).toInt()
            } else {
                heightCm.intValue
            },
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
        currentStep.intValue = step
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
        age.intValue = calculateAge(date)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun calculateAge(dateOfBirth: LocalDate): Int {
        return Period.between(dateOfBirth, LocalDate.now()).years
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onHeightUnitChange(unit: String) {
        heightUnit.value = unit
        updateDerivedStates()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onHeightFeetChange(feet: Int) {
        heightFeet.intValue = feet
        updateDerivedStates()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onHeightInchesChange(inches: Int) {
        heightInches.intValue = inches
        updateDerivedStates()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onHeightCmChange(cm: Int) {
        heightCm.intValue = cm
        updateDerivedStates()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onWeightUnitChange(unit: String) {
        weightUnit.value = unit
        updateDerivedStates()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onWeightKgChange(kg: Int) {
        weightKg.intValue = kg
        updateDerivedStates()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onWeightLbChange(lb: Int) {
        weightLb.intValue = lb
        updateDerivedStates()
    }

    fun onBackClick(navController: NavController) {
        navController.navigate("sign_in")
    }

    fun onSkipClick(navController: NavController) {
        navController.navigate("home")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onNextClick(
        profileViewModel: ProfileViewModel,
        homeViewModel: HomeViewModel
    ) {
        when (currentStep.intValue) {
            1 -> if (selectedGoal.value != null) currentStep.intValue++
            2 -> currentStep.intValue++
            3 -> currentStep.intValue++
            4 -> currentStep.intValue++
            5 -> {
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
        commitProfileData(profileViewModel, homeViewModel)
        homeViewModel.fetchRandomRecipes(profileViewModel)
        Toast.makeText(navController.context, "Profile saved successfully", Toast.LENGTH_SHORT).show()

        // Thay đổi: Không điều hướng ngay, giữ nguyên để xử lý nút thoát
        showExitDialog.value = true // Hiển thị dialog thoát
    }

    // Thêm trạng thái cho dialog thoát
    val showExitDialog = mutableStateOf(false)

    // Xử lý khi nhấn nút thoát trong dialog
    fun onExitClick(navController: NavController) {
        showExitDialog.value = false
        navController.navigate("setting") // Chuyển hướng đến màn hình setting
    }

    // Hủy dialog thoát mà không điều hướng
    fun onCancelExitClick() {
        showExitDialog.value = false
    }

    fun getHeightValueString(): String {
        return if (heightUnit.value == "ft") {
            "${heightFeet.intValue}'${heightInches.intValue}\""
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
        return if (selectedGender.value) "Male" else "Female"
    }

    fun getWeighValue(): Int {
        return if (weightUnit.value == "kg") weightKg.intValue else weightLb.intValue
    }
}