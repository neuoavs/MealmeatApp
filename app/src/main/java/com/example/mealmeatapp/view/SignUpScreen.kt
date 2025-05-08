package com.example.mealmeatapp.ui.theme.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
    navController: NavController,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        /* Title */

        TitleSignUp()
        /* End Title */

        /* Form */
        FormSignUp()
        /* End Form */

        Spacer(modifier = Modifier.height(16.dp))

//        TextButton(onClick = { navController.navigate("signin") }) {
//            Text(
//                text = "AlreadyLink have an account? Sign In",
//                color = colorResource(id = R.color.black),
//                style = MaterialTheme.typography.bodySmall
//            )
//        }

        AlreadyLink(navController)

        Text(
            text = "Or",
            color = colorResource(id = R.color.gray),
            style = MaterialTheme.typography.bodySmall
        )

        Spacer(modifier = Modifier.height(16.dp))

        GoogleAuth()
    }
}

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
fun FormSignUp() {
    var name = remember {mutableStateOf("")}
    var email = remember {mutableStateOf("")}
    var password = remember {mutableStateOf("")}
    var confirmPassword = remember {mutableStateOf("")}
    var passwordVisible = remember {mutableStateOf(false)}
    var confirmPasswordVisible = remember {mutableStateOf(false)}

    OutlinedTextField(
        value = name.value,
        onValueChange = {name.value = it},
        label = { Text("Name") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(8.dp)
    )
    // Email
    OutlinedTextField(
        value = email.value,
        onValueChange = { email.value = it },
        label = { Text("Email") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        shape = RoundedCornerShape(8.dp)
    )
    // Password
    OutlinedTextField(
        value = password.value,
        onValueChange = { password.value = it },
        label = { Text("Password") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            IconButton(onClick = {passwordVisible.value = !passwordVisible.value }) {
                Icon(
                    painter = painterResource(
                        id = if (passwordVisible.value) R.drawable.visibility else R.drawable.visibility_off // visibility and visibility_off
                    ),
                    contentDescription = "Toggle password visibility"
                )
            }
        },
        shape = RoundedCornerShape(8.dp)
    )

    OutlinedTextField(
        value = confirmPassword.value,
        onValueChange = { confirmPassword.value = it },
        label = { Text("Confirm Password") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        visualTransformation = if (confirmPasswordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            IconButton(onClick = { confirmPasswordVisible.value = !confirmPasswordVisible.value }) {
                Icon(
                    painter = painterResource(
                        id = if (confirmPasswordVisible.value) R.drawable.visibility else R.drawable.visibility_off // visibility and visibility_off
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
            // Xử lý đăng ký
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


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SignUpScreenPreview() {
    MealtimeAppTheme {
        SignUpScreen(
            navController = rememberNavController(),
        )
    }
}