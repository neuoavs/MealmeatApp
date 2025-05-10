package com.example.mealmeatapp.model

import android.util.Patterns
import com.google.firebase.auth.FirebaseAuth

data class User(
    val email: String = "",
    val password: String = "",
)

class AuthRepository {
    // Khởi tạo đối tượng FirebaseAuth để sử dụng các chức năng xác thực của Firebase
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

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
    fun signUp(user: User, confirmPassword: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        // Kiểm tra email và mật khẩu hợp lệ
        if (!isValidEmail(user.email) || !isValidPassword(user.password) || user.password != confirmPassword) {
            // Nếu thông tin không hợp lệ, gọi callback onFailure với thông báo lỗi
            onFailure(Exception("Thông tin không hợp lệ"))
            return
        }

        // Sử dụng Firebase để tạo tài khoản mới với email và mật khẩu
        auth.createUserWithEmailAndPassword(user.email, user.password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Nếu đăng ký thành công, gọi callback onSuccess
                    onSuccess()
                } else {
                    // Nếu thất bại, gọi callback onFailure với ngoại lệ từ Firebase hoặc lỗi
                    onFailure(task.exception ?: Exception("Lỗi không xác định"))
                }
            }
    }

    // Đăng nhập bằng Firebase
    fun signIn(user: User, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        if (!isValidEmail(user.email) || !isValidPassword(user.password)) {
            // Kiểm tra tính hợp lệ của email và mật khẩu
            onFailure(Exception("Email hoặc mật khẩu không hợp lệ"))
            return
        }

        // Sử dụng Firebase để đăng nhập với email và mật khẩu
        auth.signInWithEmailAndPassword(user.email, user.password)
            .addOnCompleteListener { task ->
                // Nếu đăng nhập thành công, gọi callback onSuccess
                if (task.isSuccessful) {
                    onSuccess()
                } else {
                    // Nếu thất bại, gọi callback onFailure với ngoại lệ từ Firebase hoặc lỗi
                    onFailure(task.exception ?: Exception("Lỗi đăng nhập"))
                }
            }
    }

    // Hàm gửi email đặt lại mật khẩu
    fun forgotPassword(email: String, onComplete: (Boolean, String?) -> Unit) {
        // Kiểm tra tính hợp lệ của email
        if (!isValidEmail(email)) {
            // Nếu email không hợp lệ, gọi callback onComplete với trạng thái thất bại và thông báo lỗi
            onComplete(false, "Email không hợp lệ")
            return
        }
        // Sử dụng Firebase để gửi email đặt lại mật khẩu
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Nếu gửi email thành công, gọi callback onComplete với trạng thái thành công
                    onComplete(true, null)
                } else {
                    // Nếu thất bại, gọi callback onComplete với trạng thái thất bại và thông báo lỗi
                    onComplete(false, task.exception?.message)
                }
            }
    }
}