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
    // Biến trạng thái cho email, mật khẩu, trạng thái hiển thị mật khẩu, trạng thái tải và thông báo lỗi
    val email = mutableStateOf("")
    val password = mutableStateOf("")
    val isPasswordVisibility = mutableStateOf(false)
    val isLoading = mutableStateOf(false)
    val errorMessage = mutableStateOf<String?>(null)

    private val authRepository = AuthRepository()

    /**
     * Xử lý hành động đăng nhập khi người dùng nhấn nút đăng nhập.
     * @param navController Bộ điều hướng để chuyển hướng đến màn hình chính (home).
     * @param profileViewModel ViewModel để cập nhật dữ liệu hồ sơ người dùng.
     * @param homeViewModel ViewModel để lấy danh sách công thức ngẫu nhiên.
     * @param recipePlannerViewModel ViewModel để đặt email người dùng cho việc lập kế hoạch công thức.
     */
    fun onSignInClick(
        navController: NavController,
        profileViewModel: ProfileViewModel,
        homeViewModel: HomeViewModel,
        recipePlannerViewModel: RecipePlannerViewModel
    ) {
        // Kiểm tra xem email và mật khẩu có bị để trống không
        if (email.value.isBlank() || password.value.isBlank()) {
            errorMessage.value = "Email and password cannot be empty"
            Toast.makeText(navController.context, "Please enter email and password", Toast.LENGTH_SHORT).show()
            return
        }

        // Tạo đối tượng User từ email và mật khẩu đã nhập
        val user = User(email = email.value, password = password.value)
        isLoading.value = true // Đặt trạng thái đang tải
        errorMessage.value = null // Xóa thông báo lỗi cũ
        Log.d("SignInViewModel", "Starting sign-in process for ${user.email}")

        // Thực hiện đăng nhập trong một coroutine để không chặn luồng chính
        viewModelScope.launch {
            try {
                authRepository.signIn(
                    user,
                    onSuccess = {
                        isLoading.value = false // Tắt trạng thái đang tải
                        profileViewModel.reset() // Đặt lại dữ liệu hồ sơ
                        profileViewModel.updateAuth(user) // Cập nhật dữ liệu hồ sơ với thông tin người dùng
                        recipePlannerViewModel.userEmail = user.email // Đặt email người dùng cho RecipePlannerViewModel
                        Log.d("SignInViewModel", "Sign-in successful for ${user.email}")
                        Toast.makeText(navController.context, "Sign-in successful", Toast.LENGTH_SHORT).show()
                        try {
                            homeViewModel.fetchRandomRecipes(profileViewModel) // Gọi hàm lấy công thức ngẫu nhiên
                            Log.d("SignInViewModel", "Successfully called fetchRandomRecipes")
                            // Điều hướng đến màn hình chính (home)
                            navController.navigate("home") {
                                popUpTo("sign_in") { inclusive = true } // Xóa màn hình đăng nhập khỏi stack
                                Log.d("SignInViewModel", "Navigation to home successful")
                            }
                        } catch (e: Exception) {
                            errorMessage.value = "Navigation error: ${e.message}"
                            Log.e("SignInViewModel", "Navigation error", e)
                            Toast.makeText(navController.context, "Navigation error", Toast.LENGTH_SHORT).show()
                        }
                    },
                    onFailure = { exception ->
                        isLoading.value = false // Tắt trạng thái đang tải
                        errorMessage.value = exception.message ?: "Unknown error"
                        Log.e("SignInViewModel", "Sign-in failed: ${exception.message}")
                        Toast.makeText(navController.context, "Sign-in failed", Toast.LENGTH_SHORT).show()
                    }
                )
            } catch (e: Exception) {
                isLoading.value = false // Tắt trạng thái đang tải
                errorMessage.value = "Sign-in process error: ${e.message}"
                Log.e("SignInViewModel", "Sign-in error", e)
                Toast.makeText(navController.context, "System error", Toast.LENGTH_SHORT).show()
            }
        }
    }
}