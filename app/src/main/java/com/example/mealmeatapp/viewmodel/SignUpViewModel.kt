package com.example.mealmeatapp.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.mealmeatapp.model.AuthRepository
import com.example.mealmeatapp.model.User

class SignUpViewModel : ViewModel(){
    val email = mutableStateOf("")
    val password =  mutableStateOf("")
    val confirmPassword =  mutableStateOf("")
    val isPasswordVisible = mutableStateOf(false)
    val isConfirmPasswordVisible = mutableStateOf(false)

    fun onSignUpClick(
        navController: NavController,
    ) {
        // Đăng kí vào

        navController.navigate("profile_set_up")

/*        val user = User(email = email.value, password = password.value)
        val authRepository = AuthRepository()

        if (authRepository.signUp(user, confirmPassword.value)) {
            navController.navigate("profile_set_up")

        } else {

        }*/
    }
}