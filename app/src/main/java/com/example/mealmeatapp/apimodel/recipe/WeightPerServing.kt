package com.example.mealmeatapp.apimodel.recipe

import com.google.gson.annotations.SerializedName


data class WeightPerServing (
  @SerializedName("amount") var amount: Int = 0,
  @SerializedName("unit") var unit: String = ""
)