package com.example.mealmeatapp.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealmeatapp.apimodel.recipe.Recipe
import com.example.mealmeatapp.model.MealType
import com.example.mealmeatapp.model.ScheduledMeal
import com.example.mealmeatapp.apimodel.request.ApiClient
import kotlinx.coroutines.launch
import java.time.LocalDate

class RecipePlannerViewModel : ViewModel() {
    private val _schedule = mutableStateListOf<ScheduledMeal>()
    val schedule: List<ScheduledMeal> = _schedule

    private val _allRecipes = mutableStateListOf<Recipe>()
    val allRecipes: List<Recipe> = _allRecipes

    private val MAX_RECIPES_PER_MEAL = 2

    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: String? get() = _errorMessage.value

    init {
        fetchAllRecipes()
    }

    fun setPreviewMode(isPreview: Boolean) {
        if (isPreview) {
            _allRecipes.addAll(
                listOf(
                    Recipe(id = 1, title = "Mock Recipe 1", summary = "", instructions = ""),
                    Recipe(id = 2, title = "Mock Recipe 2", summary = "", instructions = "")
                )
            )
        } else {
            fetchAllRecipes()
        }
    }

    private fun fetchAllRecipes() {
        viewModelScope.launch {
            try {
                val response = ApiClient.apiService.getRandomRecipe(includeTags = "")
                _allRecipes.clear()
                _allRecipes.addAll(response.recipes)
                _errorMessage.value = null
            } catch (e: Exception) {
                _errorMessage.value = "Failed to load recipes: ${e.message}"
            }
        }
    }

    fun scheduleMeal(date: LocalDate, mealType: MealType, recipe: Recipe) {
        val currentMeals = _schedule.count {
            it.date == date && it.mealType == mealType
        }
        if (currentMeals >= MAX_RECIPES_PER_MEAL) {
            _errorMessage.value = "Maximum $MAX_RECIPES_PER_MEAL recipes allowed for ${mealType.name}"
            return
        }
        _schedule.add(ScheduledMeal(recipe, date, mealType))
    }

    fun removeMeal(date: LocalDate, mealType: MealType, recipe: Recipe) {
        _schedule.removeAll {
            it.date == date && it.mealType == mealType && it.recipe.id == recipe.id
        }
    }

    fun mealsFor(date: LocalDate): Map<MealType, List<Recipe>> {
        return MealType.values().associateWith { type ->
            _schedule.filter { it.date == date && it.mealType == type }
                .map { it.recipe }
        }
    }
}