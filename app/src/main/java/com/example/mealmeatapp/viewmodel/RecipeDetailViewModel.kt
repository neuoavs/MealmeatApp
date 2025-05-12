package com.example.mealmeatapp.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.mealmeatapp.apimodel.recipe.Recipe
import com.example.mealmeatapp.apimodel.recipe.RecipeRepository

class RecipeDetailViewModel : ViewModel(){
    // start with no recipe chosen
    val recipe = mutableStateOf<Recipe?>(null)
    // default to not favorite
    val isFavorite = mutableStateOf(false)

    fun updateModel(recipe: Recipe) {
        this.recipe.value = recipe
        // now that recipe is non-null, check favorite status
        isFavorite.value = RecipeRepository().isFavorite(recipe)
    }

    fun updateFavoriteStatus() {
        isFavorite.value = !isFavorite.value
        if (isFavorite.value) {
            // add to favorites database
        } else {
            // remove from favorites database
        }
    }
}