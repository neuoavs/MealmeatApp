package com.example.mealmeatapp.apimodel.recipe

import com.google.gson.annotations.SerializedName


data class Nutrition (
  @SerializedName("nutrients") var nutrients: List<Nutrient> = emptyList(),
  @SerializedName("properties") var properties: List<Property> = emptyList(),
  @SerializedName("flavonoids") var flavonoids: List<Flavonoid> = emptyList(),
  @SerializedName("ingredients") var ingredients: List<Ingredient> = emptyList(),
  @SerializedName("caloricBreakdown") var caloricBreakdown: CaloricBreakdown = CaloricBreakdown(),
  @SerializedName("weightPerServing") var weightPerServing: WeightPerServing = WeightPerServing()
)