package com.example.mealmeatapp.model

data class ProfileData(
    var isDiet: Boolean = false,
    var gender: Boolean = true, // true for male, false for female
    var age: Int,
    var heightCm: Int,
    var heightFeetInches: Pair<Int, Int>, // first is feet, second is inches
    var heightUnit: String,
    var weight: Int,
    var weightUnit: String
)
