package com.example.example

import com.google.gson.annotations.SerializedName


data class AnalyzedInstruction (
  @SerializedName("name") var name: String = "",
  @SerializedName("steps") var steps: List<Step> = emptyList()
)