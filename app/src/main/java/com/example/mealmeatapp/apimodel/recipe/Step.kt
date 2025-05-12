package com.example.mealmeatapp.apimodel.recipe

import com.google.gson.annotations.SerializedName


data class Step (
  @SerializedName("number") var number: Int = 0,
  @SerializedName("step") var step: String = "",
  @SerializedName("ingredients") var ingredients: List<Ingredient> = emptyList(),
  @SerializedName("equipment") var equipment: List<Equipment> = emptyList()
)