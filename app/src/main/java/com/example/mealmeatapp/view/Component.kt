package com.example.mealmeatapp.view

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mealmeatapp.R
import com.example.mealmeatapp.viewmodel.ProfileSetUpViewModel
import com.example.mealmeatapp.viewmodel.SignInViewModel
import com.example.mealmeatapp.viewmodel.SignUpViewModel
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import androidx.compose.material3.ExperimentalMaterial3Api

// Sign In Screen
@Composable
fun TitleSignIn() {
    Text(
        text = stringResource(id = R.string.title_sign_in),
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.headlineSmall,
        modifier = Modifier
            .padding(top = 32.dp, bottom = 8.dp)
    )
    Text(
        text = stringResource(id = R.string.des_sign_in),
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.bodyMedium,
        color = colorResource(id = R.color.gray),
        modifier = Modifier
            .padding(bottom = 32.dp)
    )
}

@Composable
fun CreateLink(navController: NavController) {
    TextButton(onClick = { navController.navigate("signup") }) {
        Text(
            text = "Don't have an account? Sign Up",
            color = colorResource(id = R.color.black),
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
fun FormSignIn(
    navController: NavController,
    signInViewModel: SignInViewModel

) {
    var isPasswordVisible = true

    // Email
    OutlinedTextField(
        value = signInViewModel.email.value,
        onValueChange = {signInViewModel.email.value = it},
        label = { Text("Email")},
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        shape = RoundedCornerShape(8.dp)
    )


    // Password
    OutlinedTextField(
        value = signInViewModel.password.value,
        onValueChange = {signInViewModel.password.value = it },
        label = { Text("Password") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            IconButton(
                onClick = {
                    isPasswordVisible = !isPasswordVisible
                }) {
                Icon(
                    painter = painterResource(
                        id = if (isPasswordVisible) R.drawable.visibility else R.drawable.visibility_off // visibility and visibility_off
                    ),
                    contentDescription = "Toggle password visibility"
                )
            }
        },
        shape = RoundedCornerShape(8.dp)
    )

    TextButton(
        onClick = {
            // Chuyển tới trang Forgot
        },
        modifier = Modifier
    ) {
        Text(
            text = "Forgot password?",
            color = colorResource(id = R.color.orange),
            style = MaterialTheme.typography.bodySmall,
        )
    }

    Button(
        onClick = {
            signInViewModel.onSignInClick(navController = navController)
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .padding(horizontal = 16.dp)
            .shadow(7.dp, shape = RoundedCornerShape(25.dp)),
        shape = RoundedCornerShape(25.dp),
        colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.orange))
    ) {
        Text(
            text = "Sign In",
            color = colorResource(id = R.color.white),
            style = MaterialTheme.typography.labelLarge
        )
    }

}
// End Sign In Screen


// Sign Up Screen
@Composable
fun TitleSignUp() {
    Text(
        text = stringResource(id = R.string.title_sign_up),
        style = MaterialTheme.typography.headlineSmall,
        modifier = Modifier.padding(top = 32.dp, bottom = 8.dp)
    )
    Text(
        text = stringResource(id = R.string.des_sign_up),
        style = MaterialTheme.typography.bodyMedium,
        color = colorResource(id = R.color.gray),
        modifier = Modifier.padding(bottom = 32.dp)
    )
}

@Composable
fun FormSignUp(
    navController: NavController,
    signUpViewModel: SignUpViewModel
) {

    // Email
    OutlinedTextField(
        value = signUpViewModel.email.value,
        onValueChange = { signUpViewModel.email.value = it },
        label = { Text("Email") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        shape = RoundedCornerShape(8.dp)
    )
    // Password
    OutlinedTextField(
        value = signUpViewModel.password.value,
        onValueChange = { signUpViewModel.password.value = it },
        label = { Text("Password") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        visualTransformation = if (signUpViewModel.isPasswordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            IconButton(onClick = {
                signUpViewModel.isPasswordVisible.value = !signUpViewModel.isPasswordVisible.value
            }) {
                Icon(
                    painter = painterResource(
                        id = if (signUpViewModel.isPasswordVisible.value) R.drawable.visibility else R.drawable.visibility_off // visibility and visibility_off
                    ),
                    contentDescription = "Toggle password visibility"
                )
            }
        },
        shape = RoundedCornerShape(8.dp)
    )
    // Password Confirm
    OutlinedTextField(
        value = signUpViewModel.confirmPassword.value,
        onValueChange = { signUpViewModel.confirmPassword.value = it },
        label = { Text("Confirm Password") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        visualTransformation = if (signUpViewModel.isConfirmPasswordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            IconButton(onClick = {
                signUpViewModel.isConfirmPasswordVisible.value = !signUpViewModel.isConfirmPasswordVisible.value
            }) {
                Icon(
                    painter = painterResource(
                        id = if (signUpViewModel.isConfirmPasswordVisible.value) R.drawable.visibility else R.drawable.visibility_off // visibility and visibility_off
                    ),
                    contentDescription = "Toggle confirm password visibility"
                )
            }
        },
    )

    Spacer(modifier = Modifier.height(32.dp))

    Button(
        onClick = {
            signUpViewModel.onSignUpClick(navController = navController)
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .padding(horizontal = 16.dp)
            .shadow(7.dp, shape = RoundedCornerShape(25.dp)),
        shape = RoundedCornerShape(25.dp),
        colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.orange))
    ) {
        Text(
            text = "Sign Up",
            color = colorResource(id = R.color.white),
            style = MaterialTheme.typography.labelLarge
        )
    }
}

@Composable
fun AlreadyLink(navController: NavController) {
    TextButton(onClick = { navController.navigate("signin") }) {
        Text(
            text = "Already have an account? Sign In",
            color = colorResource(id = R.color.black),
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
fun GoogleAuth() {
    Button(
        onClick = {
            // Đăng nhập bằng google
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .padding(horizontal = 16.dp)
            .shadow(7.dp, shape = RoundedCornerShape(25.dp)),
        shape = RoundedCornerShape(25.dp),
        colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.white))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.google), //google
                contentDescription = "Google Icon",
                tint = Color.Unspecified,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Sign In with Google",
                color = colorResource(id = R.color.black),
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}
// End Sign Up Screen


// Meal Time Screen

@Composable
fun SignInLinkPage(
    navController: NavController,
) {
    TextButton(onClick = {
        navController.navigate("sign_in")
    }
    ) {
        Text(
            text = stringResource(id = R.string.sign_in_text_button),
            color = colorResource(id = R.color.white),
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
fun TitleMealMeat() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(top = 50.dp)
    ) {
        Text(
            text = "Mealmeat",
            color = colorResource(id = R.color.gold),
            style = MaterialTheme.typography.headlineLarge
        )
        Text(
            text = "Recipes & Meal Planning",
            color = colorResource(id = R.color.white),
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center,
            lineHeight = 16.sp
        )
    }
}
// End Meal Time Screen

// Profile Set Up Screen
@Composable
fun HeaderProfile(
    navController: NavController,
    profileSetUpViewModel: ProfileSetUpViewModel
) {
    Icon(
        painter = painterResource(id = R.drawable.arrow_back_ios),
        contentDescription = "Back",
        tint = Color(0xFF2E7D32),
        modifier = Modifier
            .size(40.dp)
            .clickable {
                if (profileSetUpViewModel.currentStep.intValue == 1) {
                    profileSetUpViewModel.onBackClick(
                        navController = navController
                    )
                } else {
                    profileSetUpViewModel.onStepChange(--profileSetUpViewModel.currentStep.intValue)
                }
            }
    )
    Text(
        text = "${profileSetUpViewModel.currentStep.intValue}/6",
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
@Composable
fun ProfileOption(
    text: String? = null,
    iconResId: Int,
    isSelected: Boolean,
    onClick: () -> Unit,
    layoutType: String
) {
    when (layoutType) {
        "row" -> {
            RowOption(
                text = text,
                iconResId = iconResId,
                isSelected = isSelected,
                onClick = onClick,
            )
        }
        "column" -> {
            ColumnOption(
                text = text,
                iconResId = iconResId,
                isSelected = isSelected,
                onClick = onClick,
            )
        }
    }
}

@Composable
fun RowOption(
    text: String?,
    iconResId: Int,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(
                color = if (isSelected) Color(0xFFF5F5F5) else Color.White,
                shape = RoundedCornerShape(8.dp)
            )
            .clickable { onClick() }
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text ?: "",
            fontSize = 16.sp,
            color = Color.Black,
            lineHeight = 20.sp
        )
        Icon(
            painter = painterResource(id = iconResId),
            contentDescription = "$text Icon",
            tint = Color(0xFF2E7D32),
            modifier = Modifier.size(24.dp)
        )
    }
}


@Composable
fun ColumnOption(
    text: String?,
    iconResId: Int,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .width(150.dp)
            .background(
                color = if (isSelected) Color(0xFFF5F5F5) else Color.White,
                shape = RoundedCornerShape(8.dp)
            )
            .clickable { onClick() }
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(id = iconResId),
            contentDescription = "$text Icon",
            tint = Color.Unspecified,
            modifier = Modifier
                .size(100.dp)
                .padding(bottom = 8.dp)
        )
        Text(
            text = text ?: "",
            fontSize = 16.sp,
            color = Color.Black,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun StepOne(
    profileSetUpViewModel: ProfileSetUpViewModel
) {
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
        isSelected = profileSetUpViewModel.selectedGoal.value == "Less weight",
        onClick = { profileSetUpViewModel.onGoalChange("Less weight") },
        layoutType = "row"
    )
    ProfileOption(
        text = "Gain weight",
        iconResId = R.drawable.fitness_center, // gain_weight
        isSelected = profileSetUpViewModel.selectedGoal.value == "Gain weight",
        onClick = { profileSetUpViewModel.onGoalChange("Gain weight") },
        layoutType = "row"
    )
    ProfileOption(
        text = "Stay healthy",
        iconResId = R.drawable.ecg_heart_fill, // stay_healthy
        isSelected = profileSetUpViewModel.selectedGoal.value == "Stay healthy",
        onClick = { profileSetUpViewModel.onGoalChange("Stay healthy") },
        layoutType = "row"
    )
}

@Composable
fun StepTwo(
    profileSetUpViewModel: ProfileSetUpViewModel
) {
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
            iconResId = R.drawable.male,
            isSelected = profileSetUpViewModel.selectedGender.value == true,
            onClick = { profileSetUpViewModel.onGenderChange(true) },
            layoutType = "column"
        )
        ProfileOption(
            text = "Female",
            iconResId = R.drawable.female,
            isSelected = profileSetUpViewModel.selectedGender.value == false,
            onClick = { profileSetUpViewModel.onGenderChange(false) },
            layoutType = "column"
        )
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalMaterial3Api
@Composable
fun StepThree(
    profileSetUpViewModel: ProfileSetUpViewModel
) {
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
                text = profileSetUpViewModel.age.intValue.toString(),
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
                        datePickerState.selectedDateMillis?.let { millis ->
                            profileSetUpViewModel.onDateChange(
                                LocalDate.ofEpochDay(millis / (24 * 60 * 60 * 1000))
                            )
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


@Composable
fun StepFour(
    profileSetUpViewModel: ProfileSetUpViewModel
) {
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
            color = if (profileSetUpViewModel.heightUnit.value == "ft") Color.Black else Color.Gray,
            modifier = Modifier
                .clickable {
                    profileSetUpViewModel.onHeightUnitChange("ft")
                    profileSetUpViewModel.onHeightFeetChange(profileSetUpViewModel.heightInFeetInches.value.first)
                    profileSetUpViewModel.onHeightInchesChange(profileSetUpViewModel.heightInFeetInches.value.second)
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
                    profileSetUpViewModel.onHeightCmChange(profileSetUpViewModel.heightInCm.intValue)
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
                    "${profileSetUpViewModel.heightFeet.intValue}'${profileSetUpViewModel.heightInches.intValue}\""
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
                    value = profileSetUpViewModel.heightFeet.intValue.toFloat(),
                    onValueChange = { profileSetUpViewModel.onHeightFeetChange(it.toInt()) },
                    valueRange = 3f..8f,
                    steps = 4,
                    modifier = Modifier.height(50.dp)
                )
                Slider(
                    value = profileSetUpViewModel.heightInches.intValue.toFloat(),
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
                    value = profileSetUpViewModel.heightCm.intValue.toFloat(),
                    onValueChange = { profileSetUpViewModel.onHeightCmChange(it.toInt()) },
                    valueRange = 90f..250f,
                    steps = 159,
                    modifier = Modifier.height(100.dp)
                )
            }
        }
    }
}

@Composable
fun StepFive(
    profileSetUpViewModel: ProfileSetUpViewModel
) {
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
            color = if (profileSetUpViewModel.weightUnit.value == "kg") Color.Black else Color.Gray,
            modifier = Modifier
                .clickable {
                    profileSetUpViewModel.onWeightUnitChange("kg")
                    profileSetUpViewModel.onWeightKgChange(profileSetUpViewModel.weightInKg.intValue)
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
                    profileSetUpViewModel.onWeightLbChange(profileSetUpViewModel.weightInLb.intValue)
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
                    value = profileSetUpViewModel.weightKg.intValue.toFloat(),
                    onValueChange = { profileSetUpViewModel.onWeightKgChange(it.toInt()) },
                    valueRange = 30f..150f,
                    steps = 119,
                    modifier = Modifier.height(100.dp)
                )
            } else {
                Slider(
                    value = profileSetUpViewModel.weightLb.intValue.toFloat(),
                    onValueChange = { profileSetUpViewModel.onWeightLbChange(it.toInt()) },
                    valueRange = 66f..330f,
                    steps = 263,
                    modifier = Modifier.height(100.dp)
                )
            }
        }
    }
}

@Composable
fun ProfileTable(
    profileSetUpViewModel: ProfileSetUpViewModel
) {
    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Goal: ${profileSetUpViewModel.selectedGoal.value}",
            fontSize = 16.sp,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Gender: ${
                profileSetUpViewModel.getGenderValueString()
            }",
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
            text = "Height: ${
                profileSetUpViewModel.getHeighValueString()
            }",
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
}

@Composable
fun ProfileDialog(
    profileSetUpViewModel: ProfileSetUpViewModel
) {
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
                ProfileTable(
                    profileSetUpViewModel = profileSetUpViewModel
                )
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
}

@Composable
fun NextButtonProfile(
    profileSetUpViewModel: ProfileSetUpViewModel
) {
    CustomButton(
        text = "NEXT",
        onClick = {
            when (profileSetUpViewModel.currentStep.intValue) {
                1 -> if (profileSetUpViewModel.selectedGoal.value != null) profileSetUpViewModel.onStepChange(profileSetUpViewModel.currentStep.intValue + 1)
                2 -> profileSetUpViewModel.onStepChange(profileSetUpViewModel.currentStep.intValue + 1)
                3 -> profileSetUpViewModel.onStepChange(profileSetUpViewModel.currentStep.intValue + 1)
                4 -> profileSetUpViewModel.onStepChange(profileSetUpViewModel.currentStep.intValue + 1)
                5 -> profileSetUpViewModel.onStepChange(profileSetUpViewModel.currentStep.intValue + 1)
            }

        },
        modifier = Modifier.enabled(
            when (profileSetUpViewModel.currentStep.intValue) {
                1 -> profileSetUpViewModel.selectedGoal.value != null
                2 -> true
                3 -> true
                4 -> true
                5 -> true
                else -> false
            }
        )
    )
}

@Composable
fun StepCircle(
    profileSetUpViewModel: ProfileSetUpViewModel
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(bottom = 16.dp)
    ) {
        repeat(6) { index ->
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .background(
                        color = if (index == profileSetUpViewModel.currentStep.intValue - 1) Color(0xFF2E7D32) else Color.Gray,
                        shape = CircleShape
                    )
            )
        }
    }
}

// End Profile Set Up Screen


