package com.example.mealmeatapp.apimodel.result

import com.example.mealmeatapp.apimodel.recipe.Recipe
import com.google.gson.annotations.SerializedName

data class RandomRecipesResponse(
    @SerializedName("recipes") var recipes: List<Recipe> = emptyList()
)
