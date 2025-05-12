package com.example.mealmeatapp.apimodel.request

data class ChatRequest(
    val model: String,
    val messages: List<Message>
)
data class Message(
    val role: String,
    val content: String,
    val isImage: Boolean = false, // Add isImage field
    val imageUri: String? = null
)