package com.example.example

import com.google.gson.annotations.SerializedName


data class CaloricBreakdown (
  @SerializedName("percentProtein") var percentProtein: Double = 0.0,
  @SerializedName("percentFat") var percentFat: Double = 0.0,
  @SerializedName("percentCarbs") var percentCarbs: Double = 0.0
)