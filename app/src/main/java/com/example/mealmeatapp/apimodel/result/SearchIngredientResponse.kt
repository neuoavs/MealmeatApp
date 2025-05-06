package com.example.mealmeatapp.apimodel.result

import com.example.mealmeatapp.apimodel.ingredient.Ingredient
import com.google.gson.annotations.SerializedName

data class SearchIngredientResponse(
    @SerializedName("results") var results: List<Ingredient> = emptyList<>(),
    @SerializedName("offset") var offset: Int = 0,
    @SerializedName("number") var number: Int = 0,
    @SerializedName("totalResults") var totalResults : Int = 0
)
