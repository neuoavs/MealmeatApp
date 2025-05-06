package com.example.example

import com.google.gson.annotations.SerializedName


data class Step (
  @SerializedName("number") var number: Int = 0,
  @SerializedName("step") var description: String = "",
  @SerializedName("ingredients") var ingredients: List<Ingredient> = emptyList(),
  @SerializedName("equipment") var equipment: List<Equipment> = emptyList()
)