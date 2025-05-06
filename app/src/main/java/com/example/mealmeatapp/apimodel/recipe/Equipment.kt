package com.example.example

import com.google.gson.annotations.SerializedName


data class Equipment (
  @SerializedName("id") var id: Int = 0,
  @SerializedName("name") var name: String = "",
  @SerializedName("localizedName") var localizedName: String = "",
  @SerializedName("image") var image: String = "",
  @SerializedName("temperature") var temperature: Temperature = Temperature()
)