package com.example.mealmeatapp.ui.theme.view


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*

import androidx.compose.runtime.*
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mealmeatapp.R
import com.example.mealmeatapp.controller.AuthViewModel
import com.example.mealmeatapp.ui.theme.*

@Composable
fun SignInScreen(
    navController: NavController,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        TitleSignIn()

        Spacer(modifier = Modifier.height(16.dp))

        FormSignIn()

        CreateLink(navController)

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
fun FormSignIn() {
    var email = remember {mutableStateOf("")}
    var password = remember {mutableStateOf("")}
    var passwordVisible = remember {mutableStateOf(false)}

    OutlinedTextField(
        value = email.value,
        onValueChange = {email.value = it},
        label = { Text("Password")},
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        shape = RoundedCornerShape(8.dp)
    )

    OutlinedTextField(
        value = password.value,
        onValueChange = {password.value = it },
        label = { Text("Password") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            IconButton(
                onClick = {
                    passwordVisible.value = !passwordVisible.value
                }) {
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

    TextButton(
        onClick = {
            // Chuyển tới trang Forgot
        },
        modifier = Modifier
            .padding(top = 4.dp)
    ) {
        Text(
            text = "Forgot password?",
            color = colorResource(id = R.color.orange),
            style = MaterialTheme.typography.bodySmall,
        )
    }

    Spacer(modifier = Modifier.height(16.dp))

    Button(
        onClick = {
            // Xử lý đăng nhập
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




@Preview(showBackground = true)
@Composable
fun SignInScreenPreview() {
    MealtimeAppTheme {
        SignInScreen(
            navController = rememberNavController(),
        )
    }
}
