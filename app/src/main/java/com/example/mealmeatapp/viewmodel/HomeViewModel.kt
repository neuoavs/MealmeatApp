package com.example.mealmeatapp.viewmodel

import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealmeatapp.apimodel.recipe.Recipe
import com.example.mealmeatapp.apimodel.request.ApiClient
import com.example.mealmeatapp.apimodel.result.RandomRecipesResponse
import kotlinx.coroutines.launch


class HomeViewModel() : ViewModel() {
    val query = mutableStateOf("")
    val selectedCategory = mutableStateOf("random")
    val recipes = mutableStateOf(emptyList<Recipe>())
    val categories = listOf<String>(
        "Random",
        "Breakfast",
        "Main course",
        "Salad",
        "Desert"
    )

    fun onQueryChange(search: String) {
        query.value = search
    }


    fun onCategoryChange(
        category: String,
        profileViewModel: ProfileViewModel
    ) {
        selectedCategory.value = category
        fetchRandomRecipes(profileViewModel)
    }

    fun fetchRandomRecipes(
        profileViewModel: ProfileViewModel
    ) {
        val builder = StringBuilder()

        if(profileViewModel.isDiet.value) {
            builder.append("diet")
            if (selectedCategory.value.lowercase() != "random") {
                builder.append(",").append(selectedCategory.value.lowercase())
            }
        } else {
            if (selectedCategory.value.lowercase() != "random") {
                builder.append(selectedCategory.value.lowercase())
            }
        }

        viewModelScope.launch {
            try {
                val includeTags = builder.toString()
                recipes.value = ApiClient.apiService.getRandomRecipe(includeTags = includeTags).recipes
            } catch (e: Exception) {

            }
        }
    }

    fun onRecipeClick(recipe: Recipe) {
        
    }
}