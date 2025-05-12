package com.example.mealmeatapp.apimodel.recipe

import com.google.gson.annotations.SerializedName


data class ExtendedIngredient (
  @SerializedName("id") var id: Int = 0,
  @SerializedName("aisle") var aisle: String = "",
  @SerializedName("image") var image: String = "",
  @SerializedName("consistency") var consistency: String = "",
  @SerializedName("name") var name: String = "",
  @SerializedName("nameClean") var nameClean: String = "",
  @SerializedName("original") var original: String = "",
  @SerializedName("originalName") var originalName: String = "",
  @SerializedName("amount") var amount: Double = 0.0,
  @SerializedName("unit") var unit: String = "",
  @SerializedName("meta") var meta: List<String> = emptyList(),
  @SerializedName("measures") var measures: Metric = Metric()
)