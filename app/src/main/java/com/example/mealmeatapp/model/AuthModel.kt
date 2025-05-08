package com.example.mealmeatapp.ui.theme.model

data class UserCredentials(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = ""
)

class AuthRepository {
    // Simulate authentication logic (replace with actual backend calls)
    fun signUp(credentials: UserCredentials): Boolean {
        return credentials.email.isNotEmpty() &&
                credentials.password.isNotEmpty() &&
                credentials.password == credentials.confirmPassword
    }

    fun signIn(email: String, password: String): Boolean {
        return email.isNotEmpty() && password.isNotEmpty() // Add more robust validation if needed
    }

    fun forgotPassword(email: String): Boolean {
        return email.isNotEmpty() // Simulate sending reset link
    }
}