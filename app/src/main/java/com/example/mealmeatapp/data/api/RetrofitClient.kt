package com.example.mealmeatapp.data.api

import com.example.mealmeatapp.BuildConfig
import com.example.mealmeatapp.apimodel.request.ChatRequest
import com.example.mealmeatapp.apimodel.result.ChatResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

object RetrofitClient {
    private const val BASE_URL = "https://openrouter.ai/api/v1/" // ✅ Dùng OpenRouter

    private val API_KEY = BuildConfig.OPENAI_API_KEY

    private val okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $API_KEY")
                .addHeader("Content-Type", "application/json")
                .addHeader("HTTP-Referer", "https://your-app.com") // ✅ Tùy chọn (bắt buộc với OpenRouter)
                .addHeader("X-Title", "MealMeatApp")                // ✅ Tên ứng dụng hiển thị trên OpenRouter
                .build()
            chain.proceed(request)
        }
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val openAiApi: OpenAiApi = retrofit.create(OpenAiApi::class.java)
}

interface OpenAiApi {
    @POST("chat/completions")
    suspend fun getChatCompletion(@Body request: ChatRequest): ChatResponse
}
