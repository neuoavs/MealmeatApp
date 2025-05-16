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
import com.example.mealmeatapp.viewmodel.RecipePlannerViewModel
import com.example.mealmeatapp.viewmodel.SignInViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

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

/**
 * Hiển thị liên kết để tạo tài khoản mới.
 * @param navController Bộ điều hướng để chuyển đến màn hình đăng ký.
 */
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

/**
 * Hiển thị form đăng nhập với các trường nhập liệu và nút đăng nhập.
 * @param navController Bộ điều hướng để chuyển hướng sau khi đăng nhập.
 * @param signInViewModel ViewModel xử lý logic đăng nhập.
 * @param profileViewModel ViewModel để cập nhật dữ liệu hồ sơ.
 * @param homeViewModel ViewModel để lấy danh sách công thức.
 * @param recipePlannerViewModel ViewModel để quản lý kế hoạch công thức.
 */
@Composable
fun FormSignIn(
    navController: NavController,
    signInViewModel: SignInViewModel,
    profileViewModel: ProfileViewModel,
    homeViewModel: HomeViewModel,
    recipePlannerViewModel: RecipePlannerViewModel
) {
    // Trường nhập email
    OutlinedTextField(
        value = signInViewModel.email.value,
        onValueChange = { signInViewModel.email.value = it },
        label = { Text("Email") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        shape = RoundedCornerShape(8.dp)
    )

    // Trường nhập mật khẩu
    OutlinedTextField(
        value = signInViewModel.password.value,
        onValueChange = { signInViewModel.password.value = it },
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
                        id = if (signInViewModel.isPasswordVisibility.value) R.drawable.visibility else R.drawable.visibility_off
                    ),
                    contentDescription = "Toggle password visibility"
                )
            }
        },
        shape = RoundedCornerShape(8.dp)
    )

    // Liên kết quên mật khẩu (chưa có hành động cụ thể)
    TextButton(
        onClick = {
            // Chuyển tới trang Forgot (chưa triển khai)
        },
        modifier = Modifier
            .padding(start = 16.dp)
    ) {
        Text(
            text = "Forgot password?",
            color = colorResource(id = R.color.orange),
            style = MaterialTheme.typography.bodySmall,
        )
    }

    // Nút đăng nhập
    Button(
        onClick = {
            signInViewModel.onSignInClick(
                navController = navController,
                profileViewModel = profileViewModel,
                homeViewModel = homeViewModel,
                recipePlannerViewModel = recipePlannerViewModel // Thêm tham số này
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