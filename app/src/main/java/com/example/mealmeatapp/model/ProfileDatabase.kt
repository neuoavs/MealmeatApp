package com.example.mealmeatapp.model

// Profile
data class ProfileDatabase (
    var email: String,
    var isDiet: Boolean,
    var gender: Boolean, // true for male, false for female
    var age: Int,
    var heightCm: Int,
    var heightFeet: Int,
    var heightInch: Int,
    var heightUnit: String,
    var weight: Int,
    var weightUnit: String
)
