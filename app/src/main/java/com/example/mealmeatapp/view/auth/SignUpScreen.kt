package com.example.mealmeatapp.view.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.mealmeatapp.R
import com.example.mealmeatapp.ui.theme.AppColors
import com.example.mealmeatapp.ui.theme.AppShapes
import com.example.mealmeatapp.ui.theme.AppTypography
import com.example.mealmeatapp.view.components.CustomButton

@Composable
fun SignUpScreen(onLoginClick: () -> Unit) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isTermsAccepted by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.DarkTeal)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo Icon",
                tint = AppColors.DarkGreen,
                modifier = Modifier
                    .size(70.dp)
                    .offset(y = (-20).dp)
                    .align(Alignment.Start)
                    .padding(16.dp)
            )

            Icon(
                painter = painterResource(id = R.drawable.group2),
                contentDescription = "Meals Illustration",
                tint = Color.Unspecified,
                modifier = Modifier
                    .size(175.dp)
                    .padding(bottom = 16.dp)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 300.dp)
                .background(AppColors.LightCream, shape = AppShapes.RoundedTopCorners)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Sign Up",
                style = AppTypography.TitleMedium,
                color = AppColors.Black,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            SocialSignUpOptions()

            Spacer(modifier = Modifier.height(16.dp))

            SignUpForm(
                name = name,
                onNameChange = { name = it },
                email = email,
                onEmailChange = { email = it },
                password = password,
                onPasswordChange = { password = it },
                isTermsAccepted = isTermsAccepted,
                onTermsChange = { isTermsAccepted = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomButton(
                text = "Sign Up",
                onClick = {
                    if (name.isBlank() || email.isBlank() || password.isBlank()) {
                        println("Error: All fields must be filled!")
                    } else if (!isTermsAccepted) {
                        println("Error: You must agree to the Terms and Conditions!")
                    } else {
                        println("Sign Up Successful! Name: $name, Email: $email, Password: $password")
                    }
                }
            )

            Text(
                text = "Already have an account? Log In",
                style = AppTypography.BodySmall,
                color = AppColors.Black,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .clickable { onLoginClick() }
            )
        }
    }
}

@Composable
private fun SocialSignUpOptions() {
    Text(
        text = "Or",
        style = AppTypography.BodyMedium,
        color = AppColors.Gray,
        modifier = Modifier.padding(vertical = 8.dp)
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        listOf(
            R.drawable.gg to "Google Sign Up",
            R.drawable.fb to "Facebook Sign Up",
            R.drawable.app to "Apple Sign Up"
        ).forEach { (iconId, desc) ->
            IconButton(onClick = { /* TODO: Sign up with $desc */ }) {
                Icon(
                    painter = painterResource(id = iconId),
                    contentDescription = desc,
                    tint = Color.Unspecified,
                    modifier = Modifier.size(40.dp)
                )
            }
        }
    }
}

@Composable
private fun SignUpForm(
    name: String,
    onNameChange: (String) -> Unit,
    email: String,
    onEmailChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    isTermsAccepted: Boolean,
    onTermsChange: (Boolean) -> Unit
) {
    OutlinedTextField(
        value = name,
        onValueChange = onNameChange,
        label = { Text("Name") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    )

    OutlinedTextField(
        value = email,
        onValueChange = onEmailChange,
        label = { Text("Email") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    )

    OutlinedTextField(
        value = password,
        onValueChange = onPasswordChange,
        label = { Text("Password") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = isTermsAccepted,
            onCheckedChange = onTermsChange,
            colors = CheckboxDefaults.colors(checkedColor = AppColors.DarkGreen)
        )
        Text(
            text = "I agree to the Terms and Conditions",
            style = AppTypography.BodySmall,
            color = AppColors.Black
        )
    }
}
