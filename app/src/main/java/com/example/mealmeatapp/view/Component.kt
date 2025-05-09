package com.example.mealmeatapp.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.example.mealmeatapp.viewmodel.SignInViewModel
import com.example.mealmeatapp.viewmodel.SignUpViewModel

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
fun ProfileOption(
    text: String? = null,
//    title: String? = null,
//    difficulty: String? = null,
//    duration: String? = null,
//    weightChange: String? = null,
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
// End Profile Set Up Screen