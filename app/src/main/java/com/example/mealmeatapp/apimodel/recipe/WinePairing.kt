package com.example.mealmeatapp.apimodel.recipe

import com.google.gson.annotations.SerializedName


data class WinePairing (
  @SerializedName("pairedWines") var pairedWines: List<String> = emptyList(),
  @SerializedName("pairingText") var pairingText: String = "",
  @SerializedName("productMatches") var productMatches: List<ProductMatch> = emptyList()
)
data class ProductMatch(
  @SerializedName("id") var id: Int = 0,
  @SerializedName("title") var title: String = "",
  @SerializedName("description") var description: String = "",
  @SerializedName("price") var price: String = "",
  @SerializedName("imageUrl") var imageUrl: String = ""
)