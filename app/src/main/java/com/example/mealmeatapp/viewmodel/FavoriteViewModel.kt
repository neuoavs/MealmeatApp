package com.example.mealmeatapp.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.mealmeatapp.apimodel.recipe.Recipe

class FavoriteViewModel : ViewModel() {
    val favoriteRecipes = mutableStateOf(emptyList<Recipe>())

}