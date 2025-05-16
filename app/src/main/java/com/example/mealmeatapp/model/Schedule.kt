package com.example.mealmeatapp.model

import com.example.mealmeatapp.apimodel.recipe.Recipe
import java.time.LocalDate

data class ScheduledMeal(
    val recipe : Recipe,
    val date: LocalDate,
    val mealType: MealType // BREAKFAST, LUNCH, DINNER
)
enum class MealType { BREAKFAST, LUNCH, DINNER }