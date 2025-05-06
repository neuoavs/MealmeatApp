package com.example.example

import com.google.gson.annotations.SerializedName


data class Ingredient (
  @SerializedName("id") var id: Int = 0,
  @SerializedName("name") var name: String = "",
  @SerializedName("amount") var amount: Double = 0.0,
  @SerializedName("unit") var unit: String = "",
  @SerializedName("nutrients") var nutrients: List<Nutrient> = emptyList()
)