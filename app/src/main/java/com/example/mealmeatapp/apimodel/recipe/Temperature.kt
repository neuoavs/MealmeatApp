package com.example.mealmeatapp.apimodel.recipe

import com.google.gson.annotations.SerializedName


data class Temperature (
  @SerializedName("number") var varue: Double = 0.0,
  @SerializedName("unit") var unit: String = ""
)