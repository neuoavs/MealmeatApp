package com.example.mealmeatapp.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.mealmeatapp.apimodel.recipe.Recipe
import com.example.mealmeatapp.model.MealType
import com.example.mealmeatapp.model.ScheduledMeal
import java.time.LocalDate


class MealScheduleViewModel : ViewModel() {
    private val _schedule = mutableStateListOf<ScheduledMeal>()
    val schedule: List<ScheduledMeal> = _schedule

    fun scheduleMeal(date: LocalDate, mealType: MealType, recipe: Recipe) {
        // loại bỏ nếu đã có cùng date+mealType
        _schedule.removeAll { it.date == date && it.mealType == mealType }
        _schedule.add(ScheduledMeal(recipe, date, mealType))
    }
    fun mealsFor(date: LocalDate): Map<MealType, Recipe?> {
        return MealType.values().associateWith { type ->
            _schedule.find { it.date == date && it.mealType == type }?.recipe
        }
    }
}