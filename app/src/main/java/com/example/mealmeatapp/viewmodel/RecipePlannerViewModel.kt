package com.example.mealmeatapp.viewmodel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealmeatapp.apimodel.recipe.Recipe
import com.example.mealmeatapp.apimodel.request.ApiClient
import com.example.mealmeatapp.model.MealType
import com.example.mealmeatapp.model.ScheduledMeal
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
        fetchNewRecipes()
    }

    fun setPreviewMode(isPreview: Boolean) {
        if (isPreview) {
            val mockRecipes = listOf(
                Recipe(id = 1, title = "Mock Recipe 1", summary = "", instructions = ""),
                Recipe(id = 2, title = "Mock Recipe 2", summary = "", instructions = "")
            ).shuffled()
            _allRecipes.clear()
            _allRecipes.addAll(mockRecipes)
            Log.d("RecipePlannerViewModel", "Preview mode enabled: ${mockRecipes.map { it.title }}")
        } else {
            fetchNewRecipes()
        }
    }

    fun fetchNewRecipes() {
        viewModelScope.launch {
            try {
                val response = ApiClient.apiService.getRandomRecipe(includeTags = "")
                val newRecipes = response.recipes.shuffled().filter { newRecipe ->
                    !_allRecipes.any { it.id == newRecipe.id }
                }
                _allRecipes.addAll(newRecipes)
                _errorMessage.value = null
                Log.d("RecipePlannerViewModel", "Fetched new recipes: ${newRecipes.map { it.title }}")
            } catch (e: Exception) {
                _errorMessage.value = "Failed to load recipes: ${e.message}"
                Log.e("RecipePlannerViewModel", "Error fetching recipes: ${e.message}")
            }
        }
    }

    fun scheduleMeal(date: LocalDate, mealType: MealType, recipe: Recipe) {
        val currentMeals = _schedule.count { it.date == date && it.mealType == mealType }
        if (currentMeals >= MAX_RECIPES_PER_MEAL) {
            _errorMessage.value = "Maximum $MAX_RECIPES_PER_MEAL recipes allowed for ${mealType.name}"
            Log.w("RecipePlannerViewModel", "Cannot schedule: Max recipes reached for $mealType on $date")
            return
        }
        val newScheduledMeal = ScheduledMeal(recipe = recipe, date = date, mealType = mealType)
        _schedule.add(newScheduledMeal)
        saveMealToFirebase(newScheduledMeal)
        Log.d("RecipePlannerViewModel", "Scheduled: ${recipe.title} for $mealType on $date")
    }

    fun removeMeal(date: LocalDate, mealType: MealType, recipe: Recipe) {
        val scheduledMeal = _schedule.find { it.date == date && it.mealType == mealType && it.recipe.id == recipe.id }
        if (scheduledMeal != null) {
            _schedule.remove(scheduledMeal)
            removeMealFromFirebase(scheduledMeal)
            Log.d("RecipePlannerViewModel", "Removed: ${recipe.title} for $mealType on $date")
        } else {
            Log.w("RecipePlannerViewModel", "Meal not found: ${recipe.title} for $mealType on $date")
        }
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
                    Log.d("RecipePlannerViewModel", "Meal saved to Firebase: ${scheduledMeal.recipe.title}")
                }
                .addOnFailureListener { e ->
                    _errorMessage.value = "Failed to save meal: ${e.message}"
                    _schedule.remove(scheduledMeal)
                    Log.e("RecipePlannerViewModel", "Failed to save meal: ${e.message}")
                }
        } else {
            _errorMessage.value = "User email not set"
            Log.w("RecipePlannerViewModel", "Cannot save meal: userEmail is empty")
        }
    }

    private fun removeMealFromFirebase(scheduledMeal: ScheduledMeal) {
        if (userEmail.isNotEmpty()) {
            database.child("scheduledMeals")
                .orderByChild("email")
                .equalTo(userEmail)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    @RequiresApi(Build.VERSION_CODES.O)
                    override fun onDataChange(snapshot: DataSnapshot) {
                        snapshot.children.forEach { data ->
                            val savedRecipeId = data.child("recipeId").getValue(Int::class.java)
                            val savedDate = data.child("date").getValue(String::class.java)?.let { LocalDate.parse(it) }
                            val savedMealType = data.child("mealType").getValue(String::class.java)?.let { MealType.valueOf(it) }
                            if (savedRecipeId == scheduledMeal.recipe.id &&
                                savedDate == scheduledMeal.date &&
                                savedMealType == scheduledMeal.mealType
                            ) {
                                data.ref.removeValue()
                                    .addOnSuccessListener {
                                        Log.d("RecipePlannerViewModel", "Meal removed from Firebase: ${scheduledMeal.recipe.title}")
                                    }
                                    .addOnFailureListener { e ->
                                        _errorMessage.value = "Failed to remove meal: ${e.message}"
                                        _schedule.add(scheduledMeal) // Rollback
                                        Log.e("RecipePlannerViewModel", "Failed to remove meal: ${e.message}")
                                    }
                            }
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        _errorMessage.value = "Failed to remove meal: ${error.message}"
                        Log.e("RecipePlannerViewModel", "Remove cancelled: ${error.message}")
                    }
                })
        } else {
            _errorMessage.value = "User email not set"
            Log.w("RecipePlannerViewModel", "Cannot remove meal: userEmail is empty")
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
                                val date = data.child("date").getValue(String::class.java)?.let { LocalDate.parse(it) } ?: return@forEach
                                val mealType = data.child("mealType").getValue(String::class.java)?.let { MealType.valueOf(it) } ?: return@forEach
                                val recipe = getRecipeById(recipeId)
                                if (recipe != null) {
                                    _schedule.add(ScheduledMeal(recipe, date, mealType))
                                } else {
                                    fetchRecipeAndAddToSchedule(recipeId, date, mealType)
                                }
                            } catch (e: Exception) {
                                _errorMessage.value = "Error parsing scheduled meal: ${e.message}"
                                Log.e("RecipePlannerViewModel", "Error parsing meal: ${e.message}")
                            }
                        }
                        Log.d("RecipePlannerViewModel", "Fetched ${_schedule.size} scheduled meals for ${userEmail}")
                    }

                    override fun onCancelled(error: DatabaseError) {
                        _errorMessage.value = "Failed to fetch scheduled meals: ${error.message}"
                        Log.e("RecipePlannerViewModel", "Fetch cancelled: ${error.message}")
                    }
                })
        } else {
            Log.w("RecipePlannerViewModel", "Cannot fetch meals: userEmail is empty")
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
                    Log.w("RecipePlannerViewModel", "Recipe $recipeId not found")
                }
            } catch (e: Exception) {
                _errorMessage.value = "Failed to fetch recipe $recipeId: ${e.message}"
                Log.e("RecipePlannerViewModel", "Failed to fetch recipe $recipeId: ${e.message}")
            }
        }
    }

    private fun getRecipeById(recipeId: Int): Recipe? {
        return _allRecipes.find { it.id == recipeId }
    }
}