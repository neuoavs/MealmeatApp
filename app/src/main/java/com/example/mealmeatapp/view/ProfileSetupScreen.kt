package com.example.mealmeatapp.ui.theme.view

import android.content.res.loader.ResourcesLoader
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.navigation.compose.rememberNavController
import com.example.mealmateapp.ui.theme.view.FoodDetailScreen
import com.example.mealmeatapp.R
import com.example.mealmeatapp.ui.theme.MealtimeAppTheme
import com.example.mealmeatapp.ui.theme.model.ProfileData
import com.example.mealmeatapp.ui.theme.model.ProfileOption
import com.example.mealmeatapp.ui.theme.view.enabled
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ProfileSetupScreen(
    currentStep: Int,
    selectedGoal: String?,
    selectedGender: String?,
    selectedDate: LocalDate,
    heightUnit: String,
    heightFeet: Int,
    heightInches: Int,
    heightCm: Int,
    weightUnit: String,
    weightKg: Int,
    weightLb: Int,
    onStepChange: (Int) -> Unit,
    onGoalChange: (String) -> Unit,
    onGenderChange: (String) -> Unit,
    onDateChange: (LocalDate) -> Unit,
    onHeightUnitChange: (String) -> Unit,
    onHeightFeetChange: (Int) -> Unit,
    onHeightInchesChange: (Int) -> Unit,
    onHeightCmChange: (Int) -> Unit,
    onWeightUnitChange: (String) -> Unit,
    onWeightKgChange: (Int) -> Unit,
    onWeightLbChange: (Int) -> Unit,
    onBackClick: () -> Unit,
    onSkipClick: () -> Unit,
    onNextClick: (ProfileData) -> Unit
) {
    val age = Period.between(selectedDate, LocalDate.now()).years
    val heightInCm = if (heightUnit == "ft") {
        (heightFeet * 30.48 + heightInches * 2.54).toInt()
    } else {
        heightCm
    }
    val heightInFeetInches = if (heightUnit == "cm") {
        val totalInches = (heightCm / 2.54).toInt()
        val feet = totalInches / 12
        val inches = totalInches % 12
        feet to inches
    } else {
        heightFeet to heightInches
    }
    val weightInKg = if (weightUnit == "kg") {
        weightKg
    } else {
        (weightLb / 2.2).toInt()
    }
    val weightInLb = if (weightUnit == "lb") {
        weightLb
    } else {
        (weightKg * 2.2).toInt()
    }
    var showDatePicker by remember { mutableStateOf(false) }
    var showSummaryDialog by remember { mutableStateOf(false) } // State for summary dialog
    var profileDataToSubmit by remember { mutableStateOf<ProfileData?>(null) } // Store profile data for submission

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
                painter = painterResource(id = R.drawable.arrow_back_ios), // arrow_back_ios
                contentDescription = "Back",
                tint = Color(0xFF2E7D32),
                modifier = Modifier
                    .size(40.dp)
                    .clickable {
                        if (currentStep == 1) {
                            onBackClick()
                        } else {
                            onStepChange(currentStep - 1)
                        }
                    }
            )
            Text(
                text = "$currentStep/6",
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
                    .clickable { onSkipClick() }
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
            when (currentStep) {
                1 -> {
                    Text(
                        text = stringResource(id = R.string.title_s1),
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
                    ProfileOption(
                        text = "Less weight",
                        iconResId = R.drawable.nutrition_fill, // less_weight
                        isSelected = selectedGoal == "Less weight",
                        onClick = { onGoalChange("Less weight") },
                        layoutType = "row"
                    )
                    ProfileOption(
                        text = "Gain weight",
                        iconResId = R.drawable.fitness_center, // gain_weight
                        isSelected = selectedGoal == "Gain weight",
                        onClick = { onGoalChange("Gain weight") },
                        layoutType = "row"
                    )
                    ProfileOption(
                        text = "Stay healthy",
                        iconResId = R.drawable.ecg_heart_fill, // stay_healthy
                        isSelected = selectedGoal == "Stay healthy",
                        onClick = { onGoalChange("Stay healthy") },
                        layoutType = "row"
                    )
                }
                2 -> {
                    Text(
                        text = stringResource(id = R.string.title_s2),
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
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        ProfileOption(
                            text = "Male",
                            iconResId = R.drawable.male, // male
                            isSelected = selectedGender == "Male",
                            onClick = { onGenderChange("Male") },
                            layoutType = "column"
                        )
                        ProfileOption(
                            text = "Female",
                            iconResId = R.drawable.female, // female
                            isSelected = selectedGender == "Female",
                            onClick = { onGenderChange("Female") },
                            layoutType = "column"
                        )
                    }
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
                            .clickable { showDatePicker = true }
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
                                text = selectedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                                fontSize = 16.sp,
                                color = Color.Black
                            )
                        }
                        Icon(
                            painter = painterResource(id = R.drawable.calendar_month_fill), //calendar
                            contentDescription = "Select Date",
                            tint = Color(0xFF2E7D32),
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    if (showDatePicker) {
                        val datePickerState = rememberDatePickerState(
                            initialSelectedDateMillis = selectedDate.toEpochDay() * 24 * 60 * 60 * 1000
                        )
                        DatePickerDialog(
                            onDismissRequest = { showDatePicker = false },
                            confirmButton = {
                                TextButton(
                                    onClick = {
                                        showDatePicker = false
                                        datePickerState.selectedDateMillis?.let { millis ->
                                            onDateChange(LocalDate.ofEpochDay(millis / (24 * 60 * 60 * 1000)))
                                        }
                                    }
                                ) {
                                    Text("OK")
                                }
                            },
                            dismissButton = {
                                TextButton(onClick = { showDatePicker = false }) {
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
                        text = stringResource(id = R.string.title_s4),
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
                            .padding(bottom = 16.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "ft",
                            fontSize = 16.sp,
                            color = if (heightUnit == "ft") Color.Black else Color.Gray,
                            modifier = Modifier
                                .clickable {
                                    onHeightUnitChange("ft")
                                    onHeightFeetChange(heightInFeetInches.first)
                                    onHeightInchesChange(heightInFeetInches.second)
                                }
                                .padding(horizontal = 8.dp)
                        )
                        Text(
                            text = "cm",
                            fontSize = 16.sp,
                            color = if (heightUnit == "cm") Color.Black else Color.Gray,
                            modifier = Modifier
                                .clickable {
                                    onHeightUnitChange("cm")
                                    onHeightCmChange(heightInCm)
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
                                text = if (heightUnit == "ft") {
                                    "${heightFeet}'${heightInches}\""
                                } else {
                                    "$heightCm cm"
                                },
                                fontSize = 32.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        if (heightUnit == "ft") {
                            Column(
                                modifier = Modifier
                                    .width(80.dp)
                                    .height(100.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Slider(
                                    value = heightFeet.toFloat(),
                                    onValueChange = { onHeightFeetChange(it.toInt()) },
                                    valueRange = 3f..8f,
                                    steps = 4,
                                    modifier = Modifier.height(50.dp)
                                )
                                Slider(
                                    value = heightInches.toFloat(),
                                    onValueChange = { onHeightInchesChange(it.toInt()) },
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
                                    value = heightCm.toFloat(),
                                    onValueChange = { onHeightCmChange(it.toInt()) },
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
                        text = stringResource(id = R.string.title_s5),
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
                            .padding(bottom = 16.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "kg",
                            fontSize = 16.sp,
                            color = if (weightUnit == "kg") Color.Black else Color.Gray,
                            modifier = Modifier
                                .clickable {
                                    onWeightUnitChange("kg")
                                    onWeightKgChange(weightInKg)
                                }
                                .padding(horizontal = 8.dp)
                        )
                        Text(
                            text = "lb",
                            fontSize = 16.sp,
                            color = if (weightUnit == "lb") Color.Black else Color.Gray,
                            modifier = Modifier
                                .clickable {
                                    onWeightUnitChange("lb")
                                    onWeightLbChange(weightInLb)
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
                                text = if (weightUnit == "kg") {
                                    "$weightKg"
                                } else {
                                    "$weightLb"
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
                            if (weightUnit == "kg") {
                                Slider(
                                    value = weightKg.toFloat(),
                                    onValueChange = { onWeightKgChange(it.toInt()) },
                                    valueRange = 30f..150f,
                                    steps = 119,
                                    modifier = Modifier.height(100.dp)
                                )
                            } else {
                                Slider(
                                    value = weightLb.toFloat(),
                                    onValueChange = { onWeightLbChange(it.toInt()) },
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
        if (showSummaryDialog && profileDataToSubmit != null) {
            AlertDialog(
                onDismissRequest = { showSummaryDialog = false },
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
                            text = "Goal: ${profileDataToSubmit!!.goal}",
                            fontSize = 16.sp,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Gender: ${profileDataToSubmit!!.gender}",
                            fontSize = 16.sp,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Age: ${profileDataToSubmit!!.age}",
                            fontSize = 16.sp,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Height: ${profileDataToSubmit!!.height}",
                            fontSize = 16.sp,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Weight: ${profileDataToSubmit!!.weight}",
                            fontSize = 16.sp,
                            color = Color.Black
                        )
                    }
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            showSummaryDialog = false
                            onNextClick(profileDataToSubmit!!) // Proceed to Home
                        },
                        colors = ButtonDefaults.textButtonColors(contentColor = Color(0xFF2E7D32))
                    ) {
                        Text("Confirm", fontWeight = FontWeight.Medium)
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = { showSummaryDialog = false },
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
                    when (currentStep) {
                        1 -> if (selectedGoal != null) onStepChange(currentStep + 1)
                        2 -> if (selectedGender != null) onStepChange(currentStep + 1)
                        3 -> onStepChange(currentStep + 1)
                        4 -> onStepChange(currentStep + 1)
                        5 -> {
                            val height = if (heightUnit == "ft") {
                                "${heightFeet}'${heightInches}\""
                            } else {
                                "$heightCm cm"
                            }
                            val weight = if (weightUnit == "kg") {
                                "$weightKg kg"
                            } else {
                                "$weightLb lb"
                            }
                            val profileData = ProfileData(
                                selectedGoal!!,
                                selectedGender!!,
                                age,
                                height,
                                weight,
//                                    selectedProgress!!
                            )
                            profileDataToSubmit = profileData
                            showSummaryDialog = true // Show summary dialog instead of direct navigation
                        }
//                        6 -> {
//                            if (selectedProgress != null) {
//                                val height = if (heightUnit == "ft") {
//                                    "${heightFeet}'${heightInches}\""
//                                } else {
//                                    "$heightCm cm"
//                                }
//                                val weight = if (weightUnit == "kg") {
//                                    "$weightKg kg"
//                                } else {
//                                    "$weightLb lb"
//                                }
//                                val profileData = ProfileData(
//                                    selectedGoal!!,
//                                    selectedGender!!,
//                                    age,
//                                    height,
//                                    weight,
////                                    selectedProgress!!
//                                )
//                                profileDataToSubmit = profileData
//                                showSummaryDialog = true // Show summary dialog instead of direct navigation
//                            }
//                        }
                    }
                },
                modifier = Modifier.enabled(
                    when (currentStep) {
                        1 -> selectedGoal != null
                        2 -> selectedGender != null
                        3 -> true
                        4 -> true
                        5 -> true
//                        6 -> selectedProgress != null
                        else -> false
                    }
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                repeat(5) { index ->
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .background(
                                color = if (index == currentStep - 1) Color(0xFF2E7D32) else Color.Gray,
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
            currentStep = 1,
            selectedGoal = "Less weight",
            selectedGender = "Male",
            selectedDate = LocalDate.now(),
            heightUnit = "ft",
            heightFeet = 5,
            heightInches = 10,
            heightCm = 178,
            weightUnit = "kg",
            weightKg = 70,
            weightLb = 154,
//            selectedProgress = "Steady",
            onStepChange = { /* Mock for preview */ },
            onGoalChange = { /* Mock for preview */ },
            onGenderChange = { /* Mock for preview */ },
            onDateChange = { /* Mock for preview */ },
            onHeightUnitChange = { /* Mock for preview */ },
            onHeightFeetChange = { /* Mock for preview */ },
            onHeightInchesChange = { /* Mock for preview */ },
            onHeightCmChange = { /* Mock for preview */ },
            onWeightUnitChange = { /* Mock for preview */ },
            onWeightKgChange = { /* Mock for preview */ },
            onWeightLbChange = { /* Mock for preview */ },
//            onProgressChange = { /* Mock for preview */ },
            onBackClick = { /* Mock for preview */ },
            onSkipClick = { /* Mock for preview */ },
            onNextClick = { /* Mock for preview */ }
        )
    }
}