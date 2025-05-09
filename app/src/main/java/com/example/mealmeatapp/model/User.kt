package com.example.mealmeatapp.model

import android.util.Patterns

data class User(
    val email: String = "",
    val password: String = "",
)

class AuthRepository {

    // Hàm kiểm tra định dạng email hợp lệ
    private fun isValidEmail(email: String): Boolean {
        // Sử dụng Patterns.EMAIL_ADDRESS để kiểm tra định dạng email chuẩn
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    // Hàm kiểm tra mật khẩu hợp lệ
    private fun isValidPassword(password: String): Boolean {
        // Mật khẩu cần ít nhất 8 ký tự, chứa ít nhất một chữ cái và một chữ số
        val passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$".toRegex()
        return password.matches(passwordRegex)
    }

    // Hàm đăng ký tài khoản
    fun signUp(user: User, confirmPassword: String): Boolean {
        // Kiểm tra email và mật khẩu hợp lệ
        if (!isValidEmail(user.email)) {
            println("Email không hợp lệ.")
            return false
        }

        if (!isValidPassword(user.password)) {
            println("Mật khẩu không hợp lệ. Mật khẩu phải có ít nhất 8 ký tự, chứa chữ cái viết hoa, chữ cái viết thường và một chữ số.")
            return false
        }

        // Kiểm tra mật khẩu và xác nhận mật khẩu có trùng khớp không
        if (user.password != confirmPassword) {
            println("Mật khẩu và xác nhận mật khẩu không khớp.")
            return false
        }

        // Kiểm tra các trường không rỗng
        return user.email.isNotEmpty() && user.password.isNotEmpty()
    }

    // Hàm đăng nhập
    fun signIn(user: User): Boolean {
        // Kiểm tra email và mật khẩu hợp lệ
        if (!isValidEmail(user.email)) {
            println("Email không hợp lệ.")
            return false
        }

        if (!isValidPassword(user.password)) {
            println("Mật khẩu không hợp lệ.")
            return false
        }
        // Kiểm tra các trường không rỗng
        return user.email.isNotEmpty() && user.password.isNotEmpty()
    }

    // Hàm quên mật khẩu (chưa hoàn thiện, cần logic riêng)
    fun forgotPassword(email: String): Boolean {
        return email.isNotEmpty() && isValidEmail(email)
    }
}