package com.example.mealmeatapp.apimodel.result

import com.example.mealmeatapp.apimodel.recipe.Recipe
import com.google.gson.annotations.SerializedName

data class SearchRecipeResponse(
    @SerializedName("results"      ) var results      : ArrayList<Recipe> = arrayListOf(),
    @SerializedName("offset"       ) var offset       : Int?               = null,
    @SerializedName("number"       ) var number       : Int?               = null,
    @SerializedName("totalResults" ) var totalResults : Int?               = null
)
