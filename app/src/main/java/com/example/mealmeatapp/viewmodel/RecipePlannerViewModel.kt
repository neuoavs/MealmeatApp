package com.example.mealmeatapp.viewmodel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealmeatapp.apimodel.recipe.Recipe
import com.example.mealmeatapp.model.MealType
import com.example.mealmeatapp.model.ScheduledMeal
import com.example.mealmeatapp.apimodel.request.ApiClient
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
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

    var userEmail: String = ""
        set(value) {
            field = value
            fetchScheduledMeals()
        }

    private val database = FirebaseDatabase.getInstance().reference

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
        val currentMeals = _schedule.count { it.date == date && it.mealType == mealType }
        if (currentMeals >= MAX_RECIPES_PER_MEAL) {
            _errorMessage.value = "Maximum $MAX_RECIPES_PER_MEAL recipes allowed for ${mealType.name}"
            return
        }
        val newScheduledMeal = ScheduledMeal(recipe, date, mealType)
        _schedule.add(newScheduledMeal)
        saveMealToFirebase(newScheduledMeal)
    }

    fun removeMeal(date: LocalDate, mealType: MealType, recipe: Recipe) {
        _schedule.removeAll { it.date == date && it.mealType == mealType && it.recipe.id == recipe.id }
        removeMealFromFirebase(date, mealType, recipe.id)
    }

    fun mealsFor(date: LocalDate): Map<MealType, List<Recipe>> {
        return MealType.values().associateWith { type ->
            _schedule.filter { it.date == date && it.mealType == type }
                .map { it.recipe }
        }
    }

    private fun saveMealToFirebase(scheduledMeal: ScheduledMeal) {
        if (userEmail.isNotEmpty()) {
            val mealData = mapOf(
                "email" to userEmail,
                "recipeId" to scheduledMeal.recipe.id,
                "date" to scheduledMeal.date.toString(),
                "mealType" to scheduledMeal.mealType.name
            )
            database.child("scheduledMeals").push().setValue(mealData)
                .addOnSuccessListener {
                    Log.d("RecipePlannerViewModel", "Meal scheduled for ${userEmail}")
                }
                .addOnFailureListener { e ->
                    _errorMessage.value = "Failed to save meal: ${e.message}"
                    _schedule.remove(scheduledMeal)
                }
        } else {
            _errorMessage.value = "User email not set"
        }
    }

    private fun removeMealFromFirebase(date: LocalDate, mealType: MealType, recipeId: Int) {
        if (userEmail.isNotEmpty()) {
            database.child("scheduledMeals")
                .orderByChild("email")
                .equalTo(userEmail)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    @RequiresApi(Build.VERSION_CODES.O)
                    override fun onDataChange(snapshot: DataSnapshot) {
                        snapshot.children.forEach { data ->
                            val savedRecipeId = data.child("recipeId").getValue(Int::class.java)
                            val savedDate = LocalDate.parse(data.child("date").getValue(String::class.java) ?: return@forEach)
                            val savedMealType = MealType.valueOf(data.child("mealType").getValue(String::class.java) ?: return@forEach)
                            if (savedRecipeId == recipeId && savedDate == date && savedMealType == mealType) {
                                data.ref.removeValue()
                                    .addOnSuccessListener {
                                        Log.d("RecipePlannerViewModel", "Meal removed for ${userEmail}")
                                    }
                                    .addOnFailureListener { e ->
                                        _errorMessage.value = "Failed to remove meal: ${e.message}"
                                        // Rollback: Tải lại recipe từ API nếu cần
                                        fetchRecipeAndAddToSchedule(recipeId, date, mealType)
                                    }
                            }
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        _errorMessage.value = "Database error: ${error.message}"
                    }
                })
        } else {
            _errorMessage.value = "User email not set"
        }
    }

    private fun fetchScheduledMeals() {
        if (userEmail.isNotEmpty()) {
            database.child("scheduledMeals")
                .orderByChild("email")
                .equalTo(userEmail)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    @RequiresApi(Build.VERSION_CODES.O)
                    override fun onDataChange(snapshot: DataSnapshot) {
                        _schedule.clear()
                        snapshot.children.forEach { data ->
                            try {
                                val recipeId = data.child("recipeId").getValue(Int::class.java) ?: return@forEach
                                val date = LocalDate.parse(data.child("date").getValue(String::class.java) ?: return@forEach)
                                val mealType = MealType.valueOf(data.child("mealType").getValue(String::class.java) ?: return@forEach)
                                val recipe = getRecipeById(recipeId)
                                if (recipe != null) {
                                    _schedule.add(ScheduledMeal(recipe, date, mealType))
                                } else {
                                    // Tải recipe từ API nếu không có trong _allRecipes
                                    fetchRecipeAndAddToSchedule(recipeId, date, mealType)
                                }
                            } catch (e: Exception) {
                                _errorMessage.value = "Error parsing scheduled meal: ${e.message}"
                            }
                        }
                        Log.d("RecipePlannerViewModel", "Fetched ${_schedule.size} scheduled meals for ${userEmail}")
                    }

                    override fun onCancelled(error: DatabaseError) {
                        _errorMessage.value = "Fetch cancelled: ${error.message}"
                    }
                })
        }
    }

    private fun fetchRecipeAndAddToSchedule(recipeId: Int, date: LocalDate, mealType: MealType) {
        viewModelScope.launch {
            try {
                val recipe = ApiClient.apiService.getRecipesInformationBulk(ids = recipeId.toString()).firstOrNull()
                if (recipe != null) {
                    if (!_allRecipes.any { it.id == recipe.id }) {
                        _allRecipes.add(recipe)
                    }
                    _schedule.add(ScheduledMeal(recipe, date, mealType))
                    Log.d("RecipePlannerViewModel", "Fetched recipe $recipeId and added to schedule")
                } else {
                    _errorMessage.value = "Recipe $recipeId not found"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Failed to fetch recipe $recipeId: ${e.message}"
            }
        }
    }

    private fun getRecipeById(recipeId: Int): Recipe? {
        return _allRecipes.find { it.id == recipeId }
    }
}