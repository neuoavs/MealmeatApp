package com.example.mealmeatapp.viewmodel

import androidx.compose.runtime.mutableStateOf
import com.example.mealmeatapp.apimodel.recipe.Recipe
import com.example.mealmeatapp.apimodel.recipe.RecipeRepository

class RecipeDetailViewModel {
    val recipe = mutableStateOf<Recipe?>(null)
    val isFavorite = mutableStateOf(RecipeRepository().isFavorite(recipe.value!!))

    fun updateModel (recipe: Recipe) {
        this.recipe.value = recipe
        isFavorite.value = RecipeRepository().isFavorite(recipe)
    }

    fun updateFavoriteStatus() {
        isFavorite.value = !isFavorite.value
        if (isFavorite.value) {
            // nếu đã yêu thích thì cập nhật hủy yêu thích ở database

        } else {
            // nếu chưa yêu thích thì cập nhật yêu thích ở database
        }
    }
}