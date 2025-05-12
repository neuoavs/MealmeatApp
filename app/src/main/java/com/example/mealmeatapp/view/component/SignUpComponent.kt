package com.example.mealmeatapp.view.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mealmeatapp.R
import com.example.mealmeatapp.viewmodel.ProfileViewModel
import com.example.mealmeatapp.viewmodel.SignUpViewModel


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
    signUpViewModel: SignUpViewModel,
    profileViewModel: ProfileViewModel
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
            signUpViewModel.onSignUpClick(
                navController = navController,
                profileViewModel = profileViewModel
            )
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
    TextButton(onClick = { navController.navigate("sign_in") }) {
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
