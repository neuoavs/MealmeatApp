package com.example.mealmeatapp.view.auth


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.mealmeatapp.R
import com.example.mealmeatapp.ui.theme.AppColors
import com.example.mealmeatapp.ui.theme.AppShapes
import com.example.mealmeatapp.ui.theme.AppTypography
import com.example.mealmeatapp.view.components.CustomButton



@Composable
fun LoginScreen(
    onSignUpClick: () -> Unit,
    onLoginClick: (String, String, Boolean) -> Unit // Updated to pass email, password, and rememberMe state
) {
    // State management for form inputs
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var rememberMe by remember { mutableStateOf(false) }

    // Validation: Enable login button only if email and password are not empty
    val isLoginEnabled = email.isNotBlank() && password.isNotBlank()

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
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(top = 300.dp)
                .background(AppColors.LightCream, shape = AppShapes.RoundedTopCorners)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Log In",
                style = AppTypography.TitleMedium,
                color = AppColors.Black,
                modifier = Modifier.padding(bottom = 16.dp)
            )

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
                IconButton(onClick = { /* TODO: Google Login */ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.gg),
                        contentDescription = "Google Login",
                        tint = Color.Unspecified,
                        modifier = Modifier.size(40.dp)
                    )
                }
                IconButton(onClick = { /* TODO: Facebook Login */ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.fb),
                        contentDescription = "Facebook Login",
                        tint = Color.Unspecified,
                        modifier = Modifier.size(40.dp)
                    )
                }
                IconButton(onClick = { /* TODO: Apple Login */ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.app),
                        contentDescription = "Apple Login",
                        tint = Color.Unspecified,
                        modifier = Modifier.size(40.dp)
                    )
                }
            }

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = rememberMe,
                        onCheckedChange = { rememberMe = it },
                        colors = CheckboxDefaults.colors(checkedColor = AppColors.DarkGreen)
                    )
                    Text(
                        text = "Remember me",
                        style = AppTypography.BodySmall,
                        color = AppColors.Black
                    )
                }
                Text(
                    text = "Forgot password?",
                    style = AppTypography.BodySmall,
                    color = AppColors.DarkGreen,
                    modifier = Modifier.clickable {
                        /* TODO: Forgot Password - Add navigation to Forgot Password screen */
                    }
                )
            }

            CustomButton(
                text = "Log In",
                onClick = { onLoginClick(email, password, rememberMe) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .then(if (isLoginEnabled) Modifier else Modifier.alpha(0.5f)) // Disable button if not enabled
            )

            Text(
                text = "Don't have an account? Sign Up",
                style = AppTypography.BodySmall,
                color = AppColors.Black,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .clickable { onSignUpClick() }
            )
        }
    }
}
