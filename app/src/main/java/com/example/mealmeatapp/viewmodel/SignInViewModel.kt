package com.example.mealmeatapp.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.mealmeatapp.model.AuthRepository
import com.example.mealmeatapp.model.User

class SignInViewModel : ViewModel(){
    val email = mutableStateOf("")
    val password =  mutableStateOf("")
    val isPasswordVisibility = mutableStateOf(false)

    fun onSignInClick(
        navController: NavController,
    ) {
        navController.navigate("profile_set_up")

/*        val user = User(email = email.value, password = password.value)
        val authRepository = AuthRepository()

        if (authRepository.signIn(user)) {
            navController.navigate("profile_set_up")

        } else {

        }*/
    }

}