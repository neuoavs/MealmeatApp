package com.example.example

import com.google.gson.annotations.SerializedName


data class Recipe (
@SerializedName("id") var id: Int = 0,
@SerializedName("title") var title: String = "",
@SerializedName("image") var imageUrl: String = "",
@SerializedName("readyInMinutes") var readyInMinutes: Int = 0,
@SerializedName("servings") var servings: Int = 0,
@SerializedName("sourceUrl") var sourceUrl: String = "",
@SerializedName("vegetarian") var vegetarian: Boolean = false,
@SerializedName("vegan") var vegan: Boolean = false,
@SerializedName("glutenFree") var glutenFree: Boolean = false,
@SerializedName("dairyFree") var dairyFree: Boolean = false,
@SerializedName("veryHealthy") var veryHealthy: Boolean = false,
@SerializedName("cheap") var cheap: Boolean = false,
@SerializedName("veryPopular") var veryPopular: Boolean = false,
@SerializedName("sustainable") var sustainable: Boolean = false,
@SerializedName("lowFodmap") var lowFodmap: Boolean = false,
@SerializedName("weightWatcherSmartPoints") var weightWatcherSmartPoints: Int = 0,
@SerializedName("gaps") var gaps: String = "",
@SerializedName("preparationMinutes") var preparationMinutes: Int = 0,
@SerializedName("cookingMinutes") var cookingMinutes: Int = 0,
@SerializedName("aggregateLikes") var aggregateLikes: Int = 0,
@SerializedName("healthScore") var healthScore: Double = 0.0,
@SerializedName("creditsText") var creditsText: String = "",
@SerializedName("license") var license: String = "",
@SerializedName("sourceName") var sourceName: String = "",
@SerializedName("pricePerServing") var pricePerServing: Double = 0.0,
@SerializedName("extendedIngredients") var extendedIngredients: List<ExtendedIngredient> = emptyList(),
@SerializedName("nutrition") var nutrition: Nutrition = Nutrition(),
@SerializedName("summary") var summary: String = "",
@SerializedName("cuisines") var cuisines: List<String> = emptyList(),
@SerializedName("dishTypes") var dishTypes: List<String> = emptyList(),
@SerializedName("diets") var diets: List<String> = emptyList(),
@SerializedName("occasions") var occasions: List<String> = emptyList(),
@SerializedName("winePairing") var winePairing: WinePairing = WinePairing(pairedWines = emptyList(), pairingText = "", productMatches = emptyList()),
@SerializedName("instructions") var instructions: String = "",
@SerializedName("analyzedInstructions") var analyzedInstructions: List<AnalyzedInstruction> = emptyList(),
@SerializedName("spoonacularScore") var spoonacularScore: Double = 0.0
)