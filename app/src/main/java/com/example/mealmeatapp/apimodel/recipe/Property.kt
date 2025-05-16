package com.example.mealmeatapp.apimodel.recipe

import com.google.gson.annotations.SerializedName


data class Property (
  @SerializedName("name") var name: String = "",
  @SerializedName("amount") var amount: Double = 0.0,
  @SerializedName("unit") var unit: String = ""
)