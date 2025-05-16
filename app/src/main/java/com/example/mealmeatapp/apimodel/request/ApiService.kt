package com.example.mealmeatapp.apimodel.request

import com.example.mealmeatapp.apimodel.recipe.Recipe
import com.example.mealmeatapp.apimodel.ingredient.Ingredient
import com.example.mealmeatapp.apimodel.result.RandomRecipesResponse
import com.example.mealmeatapp.apimodel.result.SearchIngredientResponse
import com.example.mealmeatapp.apimodel.result.SearchRecipeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("recipes/random")
    suspend fun getRandomRecipe(
        @Query("apiKey") apiKey: String = "0eb480b8c96c43218be96f3eecd94529",
        @Query("includeNutrition") includeNutrition: Boolean = true,
        @Query("include-tags") includeTags:  String ?= null,
        @Query("exclude-tags") excludeTags: String ?= null,
        @Query("number") number: Int = 5,
    ): RandomRecipesResponse

    @GET("recipes/complexSearch")
    suspend fun searchRepice(
        @Query("apiKey") apiKey: String = "0eb480b8c96c43218be96f3eecd94529",
        @Query("query") query: String ?= null,
        @Query("cuisine") cuisine: String ?= null,
        @Query("diet") diet: String ?= null,
        @Query("intolerances") intolerances: String ?= null,
        @Query("type") type: String ?= null,
        @Query("instructionsRequired") instructionsRequired: Boolean = true,
        @Query("sort") sort: String ?= null,
        @Query("sortDirection") sortDirection: String ?= null,
        @Query("minCarbs") minCarbs: Int ?= null,
        @Query("maxCarbs") maxCarbs: Int ?= null,
        @Query("minProtein") minProtein: Int ?= null,
        @Query("maxProtein") maxProtein: Int ?= null,
        @Query("minCalories") minCalories: Int ?= null,
        @Query("maxCalories") maxCalories: Int ?= null,
        @Query("minFat") minFat: Int ?= null,
        @Query("maxFat") maxFat: Int ?= null,
        @Query("minCalcium") minCalcium: Int ?= null,
        @Query("maxCalcium") maxCalcium: Int ?= null,
        @Query("minSugar") minSugar: Int ?= null,
        @Query("maxSugar") maxSugar: Int ?= null,
        @Query("offset") offset: Int ?= null,
        @Query("number") number: Int ?= null,
        @Query("addRecipeInformation") addRecipeInformation: Boolean = true,
        @Query("addRecipeInstructions") addRecipeInstructions: Boolean = true
    ) : SearchRecipeResponse

    @GET("recipes/informationBulk")
    suspend fun getRecipesInformationBulk(
        @Query("apiKey") apiKey: String = "0eb480b8c96c43218be96f3eecd94529",
        @Query("ids") ids: String,
        @Query("includeNutrition") includeNutrition: Boolean = true
    ): ArrayList<Recipe>

    @GET("recipes/{id}/information")
    suspend fun getInformationOfPriceByID(
        @Path("id") id: Int,
        @Query("apiKey") apiKey: String = "0eb480b8c96c43218be96f3eecd94529",
        @Query("includeNutrition") includeNutrition: Boolean = true,
        @Query("addWinePairing") addWinePairing: Boolean = false,
        @Query("addTasteData") addTasteData: Boolean = false,
    ) : Recipe

    @GET("recipes/informationBulk")
    suspend fun getInformationOfManyPricesByIDs(
        @Query("apiKey") apiKey: String = "0eb480b8c96c43218be96f3eecd94529",
        @Path("ids") ids: String ?= null, // "715538,716429"
        @Query("includeNutrition") includeNutrition: Boolean = true,
    ) : List<Recipe>

    @GET("food/ingredients/search")
    suspend fun searchIngredient(
        @Query("apiKey") apiKey: String = "0eb480b8c96c43218be96f3eecd94529",
        @Query("query") query: String ?= null,
        @Query("addChildren") addChildren: Boolean = false,
        @Query("minProteinPercent") minProteinPercent: Double ?= null,
        @Query("maxProteinPercent") maxProteinPercent: Double ?= null,
        @Query("minFatPercent") minFatPercent: Double ?= null,
        @Query("maxFatPercent") maxFatPercent: Double ?= null,
        @Query("minCarbsPercent") minCarbsPercent: Double ?= null,
        @Query("maxCarbsPercent") maxCarbsPercent: Double ?= null,
        @Query("metaInformation") metaInformation: Boolean ?= null,
        @Query("intolerances") intolerances: String ?= null, // Egg,Penut
        @Query("sort") sort: String ?= null,
        @Query("sortDirection") sortDirection: String ?= null,
        @Query("language") language: String = "en",
        @Query("offset") offset: Int ?= null,
        @Query("number") number: Int ?= null
    ) : SearchIngredientResponse

    @GET("food/ingredients/{id}/information")
    suspend fun getInformationOfIngredientByID(
        @Path("id") id: Int,
        @Query("apiKey") apiKey: String = "0eb480b8c96c43218be96f3eecd94529",
        @Query("amount") amount: Double = 100.0,
        @Query("unit") unit: String = "grams",
        @Query("locale") locale: String = "en_US",
    ) : Ingredient

}