package com.example.mealmeatapp.model

data class ProfileData(
    val email: String = "",
    val password: String = "",
    val isDiet: Boolean = false,
    val gender: Boolean = true,
    val age: Int = 0,
    val heightCm: Int = 203,
    val heightFeetInches: Pair<Int, Int> = Pair(6, 8),
    val heightUnit: String = "ft",
    val weight: Int = 71,
    val weightUnit: String = "kg"
)
