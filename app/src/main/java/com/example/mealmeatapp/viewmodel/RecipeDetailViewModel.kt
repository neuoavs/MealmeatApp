package com.example.mealmeatapp.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.mealmeatapp.apimodel.recipe.Recipe

class RecipeDetailViewModel : ViewModel(){
    // start with no recipe chosen
    val recipe = mutableStateOf<Recipe?>(null)

    fun updateModel(recipe: Recipe?) {
        this.recipe.value = recipe
    }

}