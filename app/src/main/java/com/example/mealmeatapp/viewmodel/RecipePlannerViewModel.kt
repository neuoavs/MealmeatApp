package com.example.mealmeatapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.mealmeatapp.apimodel.recipe.Recipe

class RecipePlannerViewModel : ViewModel(){
    val plannedRecipe = mutableListOf<Recipe?>(null)
    val daysOfWeek = listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")
}