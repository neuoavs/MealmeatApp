package com.example.mealmeatapp.apimodel.ingredient

import com.example.example.Nutrition
import com.google.gson.annotations.SerializedName

data class Ingredient(
    @SerializedName("id") var id: Int = 0,
    @SerializedName("original") var original: String = "",
    @SerializedName("originalName") var originalName: String = "",
    @SerializedName("name") var name: String = "",
    @SerializedName("amount") var amount: Double = 0.0,
    @SerializedName("unit") var unit: String = "",
    @SerializedName("unitShort") var unitShort: String = "",
    @SerializedName("unitLong") var unitLong: String = "",
    @SerializedName("possibleUnits") var possibleUnits: List<String> = emptyList(),
    @SerializedName("estimatedCost") var estimatedCost: EstimatedCost?= EstimatedCost(),
    @SerializedName("consistency") var consistency: String = "",
    @SerializedName("shoppingListUnits" ) var shoppingListUnits : List<String> = emptyList(),
    @SerializedName("aisle") var aisle: String = "",
    @SerializedName("image") var image: String = "",
    @SerializedName("meta") var meta: List<String> = emptyList(),
    @SerializedName("nutrition") var nutrition: Nutrition?= Nutrition(),
    @SerializedName("categoryPath") var categoryPath: List<String> = emptyList()
)

