package com.example.mealmeatapp.apimodel.recipe

import com.google.gson.annotations.SerializedName


data class Metric (
  @SerializedName("amount") var amount: Double = 0.0,
  @SerializedName("unitShort") var unitShort: String = "",
  @SerializedName("unitLong") var unitLong: String = ""
)