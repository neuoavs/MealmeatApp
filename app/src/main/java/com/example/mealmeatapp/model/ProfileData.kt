package com.example.mealmeatapp.ui.theme.model

data class ProfileData(
    var goal: String,
    var gender: Boolean, // true for male, false for female
    var age: Int,
    var heightCm: Int,
    var heightFeetInches: Pair<Int, Int>, // first is feet, second is inches
    var heightUnit: String,
    var weight: Int,
    var weightUnit: String
)
