package com.example.mealmeatapp.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.mealmeatapp.apimodel.recipe.Recipe
import com.example.mealmeatapp.model.AuthRepository
import com.example.mealmeatapp.model.User
import com.google.firebase.Firebase
import com.google.firebase.database.database
import kotlinx.coroutines.launch
import com.google.firebase.database.ktx.database
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.example.mealmeatapp.model.ProfileDatabase

class SignInViewModel : ViewModel() {
    val email = mutableStateOf("")
    val password = mutableStateOf("")
    val isPasswordVisibility = mutableStateOf(false)

    val isLoading = mutableStateOf(false)
    val errorMessage = mutableStateOf<String?>(null)

    // Khởi tạo đối tượng AuthRepository để gọi các hàm xác thực
    private val authRepository = AuthRepository()

    // Hàm xử lý khi người dùng nhấn nút đăng nhập
    fun onSignInClick(
        navController: NavController,
        profileViewModel: ProfileViewModel,
        homeViewModel: HomeViewModel
    ) {
        // Tạo đối tượng User từ email và password hiện tại
        val user = User(email = email.value, password = password.value)

        // Đặt trạng thái đang tải (loading) và xóa thông báo lỗi trước đó
        isLoading.value = true
        errorMessage.value = null

        // Sử dụng viewModelScope để chạy coroutine bất đồng bộ
        viewModelScope.launch {
            // Gọi hàm đăng nhập từ AuthRepository
            authRepository.signIn(user,
                onSuccess = {
                    // Khi đăng nhập thành công, tắt trạng thái loading và chuyển hướng đến màn hình thiết lập hồ sơ
                    isLoading.value = false
                    profileViewModel.updateAuth(user)
                    Toast.makeText(navController.context, "Sign in successfully", Toast.LENGTH_SHORT).show()

                    // Code ở đây
                    // email
                    // profileViewModel.email.value =  ProfileDatabase().email
                    // profileViewModel.heghtFeetInch.first.value =
                    // ProfileDatabase()
                    homeViewModel.fetchRandomRecipes(profileViewModel)
                    navController.navigate("home")
                },
                onFailure = { exception ->
                    // Khi đăng nhập thất bại, tắt trạng thái loading, lưu thông báo lỗi và ghi log
                    isLoading.value = false
                    errorMessage.value = exception.message ?: "Unknown error"
                    Toast.makeText(navController.context, "Sign in failed", Toast.LENGTH_SHORT).show()
                }
            )
        }
    }
}