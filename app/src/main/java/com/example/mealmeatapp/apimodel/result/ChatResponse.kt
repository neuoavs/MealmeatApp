package com.example.mealmeatapp.apimodel.result


import com.example.mealmeatapp.apimodel.request.Message

data class ChatResponse(
    val choices: List<Choice>
)

data class Choice(
    val message: Message
)