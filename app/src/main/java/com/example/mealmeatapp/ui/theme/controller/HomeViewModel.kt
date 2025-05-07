package com.example.mealmeatapp.ui.theme.controller

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.mealmeatapp.ui.theme.model.Meal
import com.example.mealmeatapp.ui.theme.model.MealRepository

class HomeViewModel(private val repository: MealRepository = MealRepository()) : ViewModel() {
    val searchQuery = mutableStateOf("")
    val selectedCategory = mutableStateOf("All")
    val meals = mutableStateOf<List<Meal>>(repository.getMeals("All")) // Explicit type
    val randomMeal = mutableStateOf<Meal?>(null)
    private val _favoriteMeals = mutableStateListOf<Meal>()
    val favoriteMeals: List<Meal> = _favoriteMeals

    init {
        println("HomeViewModel: Initial meals=${meals.value.map { it.id to it.name }}")
    }

    fun onSearchQueryChange(query: String) {
        searchQuery.value = query
        meals.value = repository.getMeals(selectedCategory.value).filter {
            it.name.lowercase().contains(query.lowercase())
        }
        println("HomeViewModel: Search query=$query, filtered meals=${meals.value.map { it.id to it.name }}")
    }

    fun onCategoryChange(category: String) {
        selectedCategory.value = category
        meals.value = repository.getMeals(category).filter {
            it.name.lowercase().contains(searchQuery.value.lowercase())
        }
        println("HomeViewModel: Category=$category, filtered meals=${meals.value.map { it.id to it.name }}")
    }

    fun onRandomMealClick() {
        randomMeal.value = repository.getRandomMeal()
        meals.value = listOfNotNull(randomMeal.value)
        println("HomeViewModel: Random meal=${randomMeal.value?.id to randomMeal.value?.name}")
    }

    fun toggleFavorite(meal: Meal) {
        if (_favoriteMeals.contains(meal)) {
            _favoriteMeals.remove(meal)
        } else {
            _favoriteMeals.add(meal)
        }
        println("HomeViewModel: Toggled favorite for meal=${meal.id to meal.name}, favorites=${_favoriteMeals.map { it.id to it.name }}")
    }

    // Quản lý danh sách kế hoạch cho MealPlannerScreen
    private val _plannedMeals = mutableStateListOf<Meal>()
    val plannedMeals: List<Meal> = _plannedMeals

    fun addPlannedMeal(meal: Meal) {
        if (!_plannedMeals.contains(meal)) {
            _plannedMeals.add(meal)
            println("HomeViewModel: Added to plan meal=${meal.id to meal.name}, planned=${_plannedMeals.map { it.id to it.name }}")
        }
    }

    fun removePlannedMeal(meal: Meal) {
        _plannedMeals.remove(meal)
        println("HomeViewModel: Removed from plan meal=${meal.id to meal.name}, planned=${_plannedMeals.map { it.id to it.name }}")
    }

    fun clearPlannedMeals() {
        _plannedMeals.clear()
        println("HomeViewModel: Cleared all planned meals")
    }

    fun addRandomPlannedMeal() {
        val randomMeal = repository.getRandomMeal() ?: return
        val daysOfWeek = listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")
        val randomDay = daysOfWeek.random() // Chọn ngày ngẫu nhiên
        val mealWithDay = randomMeal.copy(dayOfWeek = randomDay) // Gán ngày cho bữa ăn
        addPlannedMeal(mealWithDay)
        println("HomeViewModel: Added random meal=${mealWithDay.id to mealWithDay.name}, day=${mealWithDay.dayOfWeek}, planned=${_plannedMeals.map { it.id to it.name }}")
    }
}