package com.example.mealmeatapp.view.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mealmeatapp.R
import com.example.mealmeatapp.viewmodel.HomeViewModel
import com.example.mealmeatapp.viewmodel.ProfileViewModel
import com.example.mealmeatapp.viewmodel.SignInViewModel

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
    TextButton(onClick = { navController.navigate("sign_up") }) {
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
    signInViewModel: SignInViewModel,
    profileViewModel: ProfileViewModel,
    homeViewModel: HomeViewModel
) {


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
        visualTransformation = if (signInViewModel.isPasswordVisibility.value) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            IconButton(
                onClick = {
                    signInViewModel.isPasswordVisibility.value = !signInViewModel.isPasswordVisibility.value
                }) {
                Icon(
                    painter = painterResource(
                        id = if (signInViewModel.isPasswordVisibility.value) R.drawable.visibility else R.drawable.visibility_off // visibility and visibility_off
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
            signInViewModel.onSignInClick(
                navController = navController,
                profileViewModel = profileViewModel,
                homeViewModel
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
            text = "Sign In",
            color = colorResource(id = R.color.white),
            style = MaterialTheme.typography.labelLarge
        )
    }

}