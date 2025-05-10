package com.example.mealmeatapp.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.mealmeatapp.model.AuthRepository
import com.example.mealmeatapp.model.User

class SignUpViewModel : ViewModel() {
    val email = mutableStateOf("")
    val password = mutableStateOf("")
    val confirmPassword = mutableStateOf("")
    val isPasswordVisible = mutableStateOf(false)
    val isConfirmPasswordVisible = mutableStateOf(false)

    //  Khởi tạo AuthRepository
    private val authRepository = AuthRepository()

    //  Gọi đăng ký bằng Firebase
    fun onSignUpClick(navController: NavController) {
        val user = User(email = email.value, password = password.value)

        authRepository.signUp(
            user = user,
            confirmPassword = confirmPassword.value,
            onSuccess = {
                // Điều hướng nếu đăng ký thành công
                navController.navigate("profile_set_up")
            },
            onFailure = { exception ->
                //  Có thể hiển thị lỗi bằng cách lưu exception.message vào biến trạng thái để hiện lên UI
                println("Đăng ký thất bại: ${exception.message}")
            }
        )
    }
}