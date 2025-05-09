package com.example.mealmeatapp.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

import com.example.mealmeatapp.R
import com.example.mealmeatapp.ui.theme.MealtimeAppTheme
import com.example.mealmeatapp.ui.theme.model.ProfileData

import com.example.mealmeatapp.view.CustomButton
import com.example.mealmeatapp.view.ProfileOption
import com.example.mealmeatapp.view.StepOne
import com.example.mealmeatapp.view.StepTwo
import com.example.mealmeatapp.view.enabled
import org.threeten.bp.LocalDate
import org.threeten.bp.Period
import org.threeten.bp.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ProfileSetupScreen(
    navController: NavController,
    profileSetUpViewModel: ProfileSetUpViewModel
//    profileSetUpViewModel.currentStep.value: Int,
//    profileSetUpViewModel.selectedGoal.value: String?,
//    profileSetUpViewModel.selectedGender.value: String?,
//    profileSetUpViewModel.selectedDate.value: LocalDate,
//    profileSetUpViewModel.heightUnit.value: String,
//    profileSetUpViewModel.heightFeet.value: Int,
//    profileSetUpViewModel.heightInches.value: Int,
//    profileSetUpViewModel.heightCm.value: Int,
//    profileSetUpViewModel.weightUnit.value: String,
//    profileSetUpViewModel.weightKg.value: Int,
//    profileSetUpViewModel.weightLb.value: Int,

//    profileSetUpViewModel.onStepChange: (Int) -> Unit,
//    onGoalChange: (String) -> Unit,
//    onGenderChange: (String) -> Unit,
//    profileSetUpViewModel.onDateChange: (LocalDate) -> Unit,
//    profileSetUpViewModel.onHeightUnitChange: (String) -> Unit,
//    profileSetUpViewModel.onHeightFeetChange: (Int) -> Unit,
//    profileSetUpViewModel.onHeightInchesChange: (Int) -> Unit,
//    profileSetUpViewModel.onHeightCmChange: (Int) -> Unit,
//    profileSetUpViewModel.onWeightUnitChange: (String) -> Unit,
//    profileSetUpViewModel.onWeightKgChange: (Int) -> Unit,
//    profileSetUpViewModel.onWeightLbChange: (Int) -> Unit,
//
//    onBackClick: () -> Unit,
//    onSkipClick: () -> Unit,
//    profileSetUpViewModel.onNextClick: (ProfileData) -> Unit
) {

    val age = Period.between(profileSetUpViewModel.selectedDate.value, LocalDate.now()).years

    val heightInCm = if (profileSetUpViewModel.heightUnit.value == "ft") {
        (profileSetUpViewModel.heightFeet.value * 30.48 + profileSetUpViewModel.heightInches.value * 2.54).toInt()
    } else {
        profileSetUpViewModel.heightCm.value
    }
    val heightInFeetInches = if (profileSetUpViewModel.heightUnit.value == "cm") {
        val totalInches = (profileSetUpViewModel.heightCm.value / 2.54).toInt()
        val feet = totalInches / 12
        val inches = totalInches % 12
        feet to inches
    } else {
        profileSetUpViewModel.heightFeet.value to profileSetUpViewModel.heightInches.value
    }
    val weightInKg = if (profileSetUpViewModel.weightUnit.value == "kg") {
        profileSetUpViewModel.weightKg.value
    } else {
        (profileSetUpViewModel.weightLb.value / 2.2).toInt()
    }
    val weightInLb = if (profileSetUpViewModel.weightUnit.value == "lb") {
        profileSetUpViewModel.weightLb.value
    } else {
        (profileSetUpViewModel.weightKg.value * 2.2).toInt()
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF9E6))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.arrow_back_ios),
                contentDescription = "Back",
                tint = Color(0xFF2E7D32),
                modifier = Modifier
                    .size(40.dp)
                    .clickable {
                        if (profileSetUpViewModel.currentStep.value == 1) {
                            profileSetUpViewModel.onBackClick()
                        } else {
                            --profileSetUpViewModel.currentStep.value
                        }
                    }
            )
            Text(
                text = "${profileSetUpViewModel.currentStep.value}/6",
                fontSize = 16.sp,
                color = Color.Black,
                modifier = Modifier
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            )
            Text(
                text = "SKIP",
                fontSize = 16.sp,
                color = Color.Black,
                modifier = Modifier
                    .clickable { profileSetUpViewModel.onSkipClick() }
                    .padding(8.dp)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            when (profileSetUpViewModel.currentStep.value) {
                1 -> {
                    StepOne(profileSetUpViewModel = profileSetUpViewModel)
                }
                2 -> {
                    StepTwo(profileSetUpViewModel = profileSetUpViewModel)
                }
                3 -> {
                    Text(
                        text = stringResource(id = R.string.title_s3),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = stringResource(id = R.string.des_set_tup),
                        fontSize = 16.sp,
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(bottom = 32.dp)
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .background(
                                color = Color(0xFFE8F5E9),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .clickable { profileSetUpViewModel.showDatePicker.value = true }
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = age.toString(),
                                fontSize = 32.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                            Text(
                                text = profileSetUpViewModel.selectedDate.value.format(
                                    DateTimeFormatter.ofPattern("yyyy-MM-dd")
                                ),
                                fontSize = 16.sp,
                                color = Color.Black
                            )
                        }
                        Icon(
                            painter = painterResource(id = R.drawable.calendar_month_fill),
                            contentDescription = "Select Date",
                            tint = Color(0xFF2E7D32),
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    if (profileSetUpViewModel.showDatePicker.value) {
                        val datePickerState = rememberDatePickerState(
                            initialSelectedDateMillis = profileSetUpViewModel.selectedDate.value.toEpochDay() * 24 * 60 * 60 * 1000
                        )
                        DatePickerDialog(
                            onDismissRequest = { profileSetUpViewModel.showDatePicker.value = false },
                            confirmButton = {
                                TextButton(
                                    onClick = {
                                        profileSetUpViewModel.showDatePicker.value = false
                                        datePickerState.profileSetUpViewModel.selectedDate.valueMillis?.let { millis ->
                                            profileSetUpViewModel.onDateChange(LocalDate.ofEpochDay(millis / (24 * 60 * 60 * 1000)))
                                        }
                                    }
                                ) {
                                    Text("OK")
                                }
                            },
                            dismissButton = {
                                TextButton(onClick = { profileSetUpViewModel.showDatePicker.value = false }) {
                                    Text("Cancel")
                                }
                            }
                        ) {
                            DatePicker(state = datePickerState)
                        }
                    }
                }
                4 -> {
                    Text(
                        text = "HOW TALL ARE YOU?",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = "We will use this data to give you a better diet type for you",
                        fontSize = 16.sp,
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(bottom = 32.dp)
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "ft",
                            fontSize = 16.sp,
                            color = if (profileSetUpViewModel.heightUnit.value == "ft") Color.Black else Color.Gray,
                            modifier = Modifier
                                .clickable {
                                    profileSetUpViewModel.onHeightUnitChange("ft")
                                    profileSetUpViewModel.onHeightFeetChange(heightInFeetInches.first)
                                    profileSetUpViewModel.onHeightInchesChange(heightInFeetInches.second)
                                }
                                .padding(horizontal = 8.dp)
                        )
                        Text(
                            text = "cm",
                            fontSize = 16.sp,
                            color = if (profileSetUpViewModel.heightUnit.value == "cm") Color.Black else Color.Gray,
                            modifier = Modifier
                                .clickable {
                                    profileSetUpViewModel.onHeightUnitChange("cm")
                                    profileSetUpViewModel.onHeightCmChange(heightInCm)
                                }
                                .padding(horizontal = 8.dp)
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(100.dp)
                                .background(
                                    color = Color(0xFFE8F5E9),
                                    shape = RoundedCornerShape(8.dp)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = if (profileSetUpViewModel.heightUnit.value == "ft") {
                                    "${profileSetUpViewModel.heightFeet.value}'${profileSetUpViewModel.heightInches.value}\""
                                } else {
                                    "$profileSetUpViewModel.heightCm.value cm"
                                },
                                fontSize = 32.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        if (profileSetUpViewModel.heightUnit.value == "ft") {
                            Column(
                                modifier = Modifier
                                    .width(80.dp)
                                    .height(100.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Slider(
                                    value = profileSetUpViewModel.heightFeet.value.toFloat(),
                                    onValueChange = { profileSetUpViewModel.onHeightFeetChange(it.toInt()) },
                                    valueRange = 3f..8f,
                                    steps = 4,
                                    modifier = Modifier.height(50.dp)
                                )
                                Slider(
                                    value = profileSetUpViewModel.heightInches.value.toFloat(),
                                    onValueChange = { profileSetUpViewModel.onHeightInchesChange(it.toInt()) },
                                    valueRange = 0f..11f,
                                    steps = 11,
                                    modifier = Modifier.height(50.dp)
                                )
                            }
                        } else {
                            Column(
                                modifier = Modifier
                                    .width(80.dp)
                                    .height(100.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Slider(
                                    value = profileSetUpViewModel.heightCm.value.toFloat(),
                                    onValueChange = { profileSetUpViewModel.onHeightCmChange(it.toInt()) },
                                    valueRange = 90f..250f,
                                    steps = 159,
                                    modifier = Modifier.height(100.dp)
                                )
                            }
                        }
                    }
                }
                5 -> {
                    Text(
                        text = "WHAT IS YOUR WEIGHT?",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = "We will use this data to give you a better diet type for you",
                        fontSize = 16.sp,
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(bottom = 32.dp)
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "kg",
                            fontSize = 16.sp,
                            color = if (profileSetUpViewModel.weightUnit.value == "kg") Color.Black else Color.Gray,
                            modifier = Modifier
                                .clickable {
                                    profileSetUpViewModel.onWeightUnitChange("kg")
                                    profileSetUpViewModel.onWeightKgChange(weightInKg)
                                }
                                .padding(horizontal = 8.dp)
                        )
                        Text(
                            text = "lb",
                            fontSize = 16.sp,
                            color = if (profileSetUpViewModel.weightUnit.value == "lb") Color.Black else Color.Gray,
                            modifier = Modifier
                                .clickable {
                                    profileSetUpViewModel.onWeightUnitChange("lb")
                                    profileSetUpViewModel.onWeightLbChange(weightInLb)
                                }
                                .padding(horizontal = 8.dp)
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(100.dp)
                                .background(
                                    color = Color(0xFFE8F5E9),
                                    shape = RoundedCornerShape(8.dp)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = if (profileSetUpViewModel.weightUnit.value == "kg") {
                                    "$profileSetUpViewModel.weightKg.value"
                                } else {
                                    "$profileSetUpViewModel.weightLb.value"
                                },
                                fontSize = 32.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Column(
                            modifier = Modifier
                                .width(80.dp)
                                .height(100.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            if (profileSetUpViewModel.weightUnit.value == "kg") {
                                Slider(
                                    value = profileSetUpViewModel.weightKg.value.toFloat(),
                                    onValueChange = { profileSetUpViewModel.onWeightKgChange(it.toInt()) },
                                    valueRange = 30f..150f,
                                    steps = 119,
                                    modifier = Modifier.height(100.dp)
                                )
                            } else {
                                Slider(
                                    value = profileSetUpViewModel.weightLb.value.toFloat(),
                                    onValueChange = { profileSetUpViewModel.onWeightLbChange(it.toInt()) },
                                    valueRange = 66f..330f,
                                    steps = 263,
                                    modifier = Modifier.height(100.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        // Summary Dialog
        if (profileSetUpViewModel.showSummaryDialog.value && profileSetUpViewModel.profileDataToSubmit.value != null) {
            AlertDialog(
                onDismissRequest = { profileSetUpViewModel.showSummaryDialog.value = false },
                title = {
                    Text(
                        text = "Profile Summary",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        textAlign = TextAlign.Center
                    )
                },
                text = {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = "Goal: ${profileSetUpViewModel.profileDataToSubmit.value!!.goal}",
                            fontSize = 16.sp,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Gender: ${profileSetUpViewModel.profileDataToSubmit.value!!.gender}",
                            fontSize = 16.sp,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Age: ${profileSetUpViewModel.profileDataToSubmit.value!!.age}",
                            fontSize = 16.sp,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Height: ${profileSetUpViewModel.profileDataToSubmit.value!!.height}",
                            fontSize = 16.sp,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Weight: ${profileSetUpViewModel.profileDataToSubmit.value!!.weight}",
                            fontSize = 16.sp,
                            color = Color.Black
                        )
                    }
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            profileSetUpViewModel.showSummaryDialog.value = false
                            profileSetUpViewModel.onNextClick(profileSetUpViewModel.profileDataToSubmit.value!!) // Proceed to Home
                        },
                        colors = ButtonDefaults.textButtonColors(contentColor = Color(0xFF2E7D32))
                    ) {
                        Text("Confirm", fontWeight = FontWeight.Medium)
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = { profileSetUpViewModel.showSummaryDialog.value = false },
                        colors = ButtonDefaults.textButtonColors(contentColor = Color.Gray)
                    ) {
                        Text("Back", fontWeight = FontWeight.Medium)
                    }
                },
                containerColor = Color(0xFFFFF9E6),
                titleContentColor = Color.Black,
                textContentColor = Color.Black
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CustomButton(
                text = "NEXT",
                onClick = {
                    when (profileSetUpViewModel.currentStep.value) {
                        1 -> if (profileSetUpViewModel.selectedGoal.value != null) profileSetUpViewModel.onStepChange(profileSetUpViewModel.currentStep.value + 1)
                        2 -> if (profileSetUpViewModel.selectedGender.value != null) profileSetUpViewModel.onStepChange(profileSetUpViewModel.currentStep.value + 1)
                        3 -> profileSetUpViewModel.onStepChange(profileSetUpViewModel.currentStep.value + 1)
                        4 -> profileSetUpViewModel.onStepChange(profileSetUpViewModel.currentStep.value + 1)
                        5 -> profileSetUpViewModel.onStepChange(profileSetUpViewModel.currentStep.value + 1)

                    }
                },
                modifier = Modifier.enabled(
                    when (profileSetUpViewModel.currentStep.value) {
                        1 -> profileSetUpViewModel.selectedGoal.value != null
                        2 -> profileSetUpViewModel.selectedGender.value != null
                        3 -> true
                        4 -> true
                        5 -> true
                        else -> false
                    }
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                repeat(6) { index ->
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .background(
                                color = if (index == profileSetUpViewModel.currentStep.value - 1) Color(0xFF2E7D32) else Color.Gray,
                                shape = CircleShape
                            )
                    )
                }
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun ProfileSetupPreview() {
    MealtimeAppTheme {
        ProfileSetupScreen(
            navController = rememberNavController() ,
            profileSetUpViewModel = ProfileSetUpViewModel()
        )
    }
}