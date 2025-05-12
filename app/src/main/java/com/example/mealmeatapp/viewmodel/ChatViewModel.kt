// com.example.mealmeatapp.viewmodel/ChatViewModel.kt
package com.example.mealmeatapp.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealmeatapp.BuildConfig
import com.example.mealmeatapp.apimodel.request.ChatRequest
import com.example.mealmeatapp.apimodel.request.Message
import com.example.mealmeatapp.data.api.RetrofitClient
import kotlinx.coroutines.launch

open class ChatViewModel : ViewModel() {
    val messages = mutableStateListOf<Message>()
    private val _isLoading = mutableStateOf(false)
    val isLoading: Boolean
        get() = _isLoading.value

    fun sendMessage(userMessage: String) {
        if (userMessage.isBlank()) return // Kiểm tra đầu vào rỗng

        // Thêm tin nhắn người dùng
        messages.add(Message("user", userMessage))
        _isLoading.value = true
        // Gửi tin nhắn bất đồng bộ
        viewModelScope.launch {
            try {
                // gửi message qua API...
            } finally {
                _isLoading.value = false
            }
        }

        viewModelScope.launch {
            try {
                // In API Key để kiểm tra (CHỈ khi debug)
                Log.d("API_KEY_CHECK", BuildConfig.OPENAI_API_KEY)

                // Gửi yêu cầu với toàn bộ lịch sử cuộc trò chuyện
                val chatRequest = ChatRequest(
                    model = "mistralai/mistral-7b-instruct", // ✅ Sửa đúng model OpenRouter
                    messages = messages.toList()
                )

                val chatResponse = RetrofitClient.openAiApi.getChatCompletion(chatRequest)
                if (chatResponse.choices.isNotEmpty()) {
                    val botMessage = chatResponse.choices.first().message.content
                    messages.add(Message("assistant", botMessage))
                } else {
                    messages.add(Message("assistant", "Không nhận được phản hồi từ server."))
                }
            } catch (e: Exception) {
                e.printStackTrace()
                messages.add(Message("assistant", "Lỗi: ${e.localizedMessage}"))
            }
        }
    }
}