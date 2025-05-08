package com.example.mealmeatapp.ui.theme.view


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mealmeatapp.R
import com.example.mealmeatapp.controller.AuthViewModel
import com.example.mealmeatapp.ui.theme.*
@Composable
fun SignUpScreen(
    viewModel: AuthViewModel,
    navController: NavController,
    onSignUpSuccess: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Getting Started",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(top = 32.dp, bottom = 8.dp)
        )
        Text(
            text = "Create an account to continue with the app",
            style = MaterialTheme.typography.bodyMedium,
            color = Gray,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        OutlinedTextField(
            value = viewModel.userCredentials.value.name,
            onValueChange = { viewModel.userCredentials.value = viewModel.userCredentials.value.copy(name = it) },
            label = { Text("Name") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            shape = RoundedCornerShape(8.dp)
        )

        OutlinedTextField(
            value = viewModel.userCredentials.value.email,
            onValueChange = { viewModel.userCredentials.value = viewModel.userCredentials.value.copy(email = it) },
            label = { Text("Email") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            shape = RoundedCornerShape(8.dp)
        )

        OutlinedTextField(
            value = viewModel.userCredentials.value.password,
            onValueChange = { viewModel.userCredentials.value = viewModel.userCredentials.value.copy(password = it) },
            label = { Text("Password") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            visualTransformation = if (viewModel.passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                IconButton(onClick = { viewModel.togglePasswordVisibility() }) {
                    Icon(
                        painter = painterResource(
                            id = if (viewModel.passwordVisible.value) R.drawable.visibility else R.drawable.visibility_off // visibility and visibility_off
                        ),
                        contentDescription = "Toggle password visibility"
                    )
                }
            },
            shape = RoundedCornerShape(8.dp)
        )

        OutlinedTextField(
            value = viewModel.confirmPassword.value,
            onValueChange = { viewModel.confirmPassword.value = it },
            label = { Text("Confirm Password") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            visualTransformation = if (viewModel.confirmPasswordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                IconButton(onClick = { viewModel.toggleConfirmPasswordVisibility() }) {
                    Icon(
                        painter = painterResource(
                            id = if (viewModel.confirmPasswordVisible.value) R.drawable.visibility else R.drawable.visibility_off // visibility and visibility_off
                        ),
                        contentDescription = "Toggle confirm password visibility"
                    )
                }
            },
            shape = RoundedCornerShape(8.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                viewModel.onSignUp()
                onSignUpSuccess()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(25.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Orange)
        ) {
            Text(
                text = "Sign Up",
                color = White,
                style = MaterialTheme.typography.labelLarge
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = { navController.navigate("signin") }) {
            Text(
                text = "Already have an account? Sign In",
                color = Black,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SignUpScreenPreview() {
    MealtimeAppTheme {
        SignUpScreen(
            viewModel = AuthViewModel(),
            navController = rememberNavController(),
            onSignUpSuccess = {}
        )
    }
}