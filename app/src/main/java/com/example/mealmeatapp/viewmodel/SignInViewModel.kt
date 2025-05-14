package com.example.mealmeatapp.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.mealmeatapp.model.AuthRepository
import com.example.mealmeatapp.model.User
import kotlinx.coroutines.launch

class SignInViewModel : ViewModel() {
    val email = mutableStateOf("")
    val password = mutableStateOf("")
    val isPasswordVisibility = mutableStateOf(false)
    val isLoading = mutableStateOf(false)
    val errorMessage = mutableStateOf<String?>(null)

    private val authRepository = AuthRepository()

    fun onSignInClick(
        navController: NavController,
        profileViewModel: ProfileViewModel,
        homeViewModel: HomeViewModel
    ) {
        if (email.value.isBlank() || password.value.isBlank()) {
            errorMessage.value = "Email và mật khẩu không được để trống"
            Toast.makeText(navController.context, "Vui lòng nhập email và mật khẩu", Toast.LENGTH_SHORT).show()
            return
        }

        val user = User(email = email.value, password = password.value)
        isLoading.value = true
        errorMessage.value = null
        Log.d("SignInViewModel", "Bắt đầu đăng nhập cho ${user.email}")

        viewModelScope.launch {
            try {
                authRepository.signIn(
                    user,
                    onSuccess = {
                        isLoading.value = false
                        profileViewModel.reset()
                        profileViewModel.updateAuth(user)
                        Log.d("SignInViewModel", "Đăng nhập thành công cho ${user.email}")
                        Toast.makeText(navController.context, "Đăng nhập thành công", Toast.LENGTH_SHORT).show()
                        try {
                            homeViewModel.fetchRandomRecipes(profileViewModel)
                            Log.d("SignInViewModel", "Gọi fetchRandomRecipes thành công")
                            navController.navigate("home") {
                                popUpTo("sign_in") { inclusive = true }
                                Log.d("SignInViewModel", "Điều hướng đến home thành công")
                            }
                        } catch (e: Exception) {
                            errorMessage.value = "Lỗi khi điều hướng: ${e.message}"
                            Log.e("SignInViewModel", "Lỗi điều hướng", e)
                            Toast.makeText(navController.context, "Lỗi điều hướng", Toast.LENGTH_SHORT).show()
                        }
                    },
                    onFailure = { exception ->
                        isLoading.value = false
                        errorMessage.value = exception.message ?: "Lỗi không xác định"
                        Log.e("SignInViewModel", "Đăng nhập thất bại: ${exception.message}")
                        Toast.makeText(navController.context, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show()
                    }
                )
            } catch (e: Exception) {
                isLoading.value = false
                errorMessage.value = "Lỗi trong quá trình đăng nhập: ${e.message}"
                Log.e("SignInViewModel", "Lỗi đăng nhập", e)
                Toast.makeText(navController.context, "Lỗi hệ thống", Toast.LENGTH_SHORT).show()
            }
        }
    }
}