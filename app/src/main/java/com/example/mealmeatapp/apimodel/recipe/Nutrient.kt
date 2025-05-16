package com.example.mealmeatapp.apimodel.recipe

import com.google.gson.annotations.SerializedName


data class Nutrient (
  @SerializedName("name") var name: String = "",
  @SerializedName("amount") var amount: Double = 0.0,
  @SerializedName("unit") var unit: String = "",
  @SerializedName("percentOfDailyNeeds") var percentOfDailyNeeds: Double = 0.0
)