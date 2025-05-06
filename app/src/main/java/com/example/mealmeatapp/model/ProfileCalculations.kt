package com.example.mealmeatapp.model

import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class NutritionPlan(
    val dailyCalories: Int,
    val carbsPercent: Int,
    val proteinPercent: Int,
    val fatPercent: Int,
    val dailyWaterIntake: Int,
    val targetWeight: Float,
    val weightChangeText: String,
    val weeklyChangeText: String,
    val endDateText: String,
    val weights: List<Float>
)

object ProfileCalculations {
    fun calculateNutritionPlan(
        profileData: ProfileData,
        weeks: Int = 12,
        startDate: LocalDate = LocalDate.of(2025, 4, 21)
    ): NutritionPlan {

        // Extract and parse user data
        val currentWeight = profileData.weight.split(" ")[0].toFloatOrNull() ?: 70f
        val heightCm = if (profileData.height.contains("cm")) {
            profileData.height.split(" ")[0].toFloatOrNull() ?: 170f
        } else {
            val (feet, inches) = profileData.height.split("'").let {
                val feetPart = it[0].toFloatOrNull() ?: 5f
                val inchesPart = it[1].replace("\"", "").toFloatOrNull() ?: 0f
                feetPart to inchesPart
            }
            (feet * 30.48f + inches * 2.54f)
        }
        val isMale = profileData.gender == "Male"
        val age = profileData.age

        // Calculate BMR using Harris-Benedict Equation
        val bmr = if (isMale) {
            10f * currentWeight + 6.25f * heightCm - 5f * age + 5f
        } else {
            10f * currentWeight + 6.25f * heightCm - 5f * age - 161f
        }

        // Adjust calories based on activity level (Progress)
        val activityFactor = when (profileData.progress) {
            "Power UP" -> 1.725f
            "Steady" -> 1.55f
            "Relaxed" -> 1.375f
            else -> 1.55f
        }
        val dailyCalories = (bmr * activityFactor).toInt()

        // Adjust macros based on goal
        val (carbsPercent, proteinPercent, fatPercent) = when (profileData.goal) {
            "Less weight" -> Triple(30, 40, 30)
            "Gain weight" -> Triple(50, 30, 20)
            "Stay healthy" -> Triple(40, 30, 30)
            else -> Triple(40, 30, 30)
        }

        // Calculate daily water intake
        val dailyWaterIntake = (currentWeight * 33).toInt()

        // Calculate weight progress
        val weeklyChange = when (profileData.goal) {
            "Less weight" -> when (profileData.progress) {
                "Power UP" -> -1f
                "Steady" -> -0.75f
                "Relaxed" -> -0.5f
                else -> -0.75f
            }
            "Gain weight" -> when (profileData.progress) {
                "Power UP" -> 1f
                "Steady" -> 0.78f
                "Relaxed" -> 0.5f
                else -> 0.78f
            }
            "Stay healthy" -> 0f
            else -> 0f
        }
        val totalChange = weeklyChange * weeks
        val targetWeight = currentWeight + totalChange
        val weightChangeText = if (totalChange >= 0) "+${totalChange} kg" else "${totalChange} kg"
        val weeklyChangeText = "${kotlin.math.abs(weeklyChange)} kg/week"

        // Calculate end date
        val endDate = startDate.plusWeeks(weeks.toLong())
        val formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")
        val endDateText = endDate.format(formatter)

        // Generate weight data points for the graph
        val weights = mutableListOf<Float>()
        var current = currentWeight
        for (i in 0 until weeks) {
            weights.add(current)
            current += weeklyChange
        }

        return NutritionPlan(
            dailyCalories = dailyCalories,
            carbsPercent = carbsPercent,
            proteinPercent = proteinPercent,
            fatPercent = fatPercent,
            dailyWaterIntake = dailyWaterIntake,
            targetWeight = targetWeight,
            weightChangeText = weightChangeText,
            weeklyChangeText = weeklyChangeText,
            endDateText = endDateText,
            weights = weights
        )
    }
}
