package com.example.mealmeatapp.viewmodel

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.mealmeatapp.apimodel.recipe.Recipe
import com.example.mealmeatapp.model.ProfileData
import com.example.mealmeatapp.model.User

class ProfileViewModel : ViewModel(){
    val email = mutableStateOf("")
    val password = mutableStateOf("")

    val isDiet = mutableStateOf(false)
    val gender = mutableStateOf(true) // true for male, false for female
    val age = mutableIntStateOf(0)
    val heightCm = mutableIntStateOf(203)
    val heightFeetInches = mutableStateOf(Pair(6, 8)) // first feet, second inches
    val heightUnit = mutableStateOf("ft")
    val weight = mutableIntStateOf(71)
    val weightUnit = mutableStateOf("kg")

    val addedRecipe =  mutableStateListOf<Recipe>()
    val favoriteRecipe = mutableStateListOf<Recipe>()

    init {
        fetchFavoriteRecipes()
        fetchAddedRecipes()
    }


    fun updateAuth(user: User) {
        email.value = user.email
        password.value = user.password
        updateProfileDatabase()
    }

    fun updateDiet(isDiet: Boolean) {
        this.isDiet.value = isDiet
    }
    fun updateGender(gender: Boolean) {
        this.gender.value = gender
    }
    fun updateAge(age: Int) {
        this.age.value = age
    }
    fun updateHeightCm(heightCm: Int) {
        this.heightCm.value = heightCm
    }
    fun updateHeightFeetInches(heightFeetInches: Pair<Int, Int>) {
        this.heightFeetInches.value = heightFeetInches
    }

    fun updateWeight(weight: Int) {
        this.weight.value = weight
    }
    fun updateWeightUnit(weightUnit: String) {
        this.weightUnit.value = weightUnit
    }
    fun updateHeightUnit(heightUnit: String) {
        this.heightUnit.value = heightUnit
    }

    fun mergeProfile(profileData: ProfileData) {
        isDiet.value = profileData.isDiet == false
        gender.value = profileData.gender
        age.value = profileData.age
        heightCm.value = profileData.heightCm
        heightFeetInches.value = profileData.heightFeetInches
        heightUnit.value = profileData.heightUnit
        weight.value = profileData.weight
        weightUnit.value = profileData.weightUnit
    }


    // Database zone
    private fun updateProfileDatabase() {
        // Phần này là đẩy dữ  profile lên database

    }

    fun addRecipe(recipe: Recipe?) {
        if (addedRecipe.none { it.id == recipe?.id }) {
            recipe?.let { addedRecipe.add(it)}
            // TODO: add to Firebase
        }
    }

    fun removeRecipe(recipe: Recipe?) {
        if (addedRecipe.none { it.id == recipe?.id }) {
            recipe?.let { addedRecipe.remove(it)}
            // TODO: add to Firebase
        }

    }

    fun addFavoriteRecipe(recipe: Recipe?) {
        if (favoriteRecipe.none { it.id == recipe?.id }) {
            recipe?.let { favoriteRecipe.add(it)}
            // TODO: add to Firebase
        }
    }

    fun removeFavoriteRecipe(recipe: Recipe?) {
        if (favoriteRecipe.none { it.id == recipe?.id }) {
            recipe?.let { favoriteRecipe.remove(it)}
            // TODO: add to Firebase
        }
        // TODO: remove from Firebase
    }

    private fun fetchFavoriteRecipes() {

    }

    private fun fetchAddedRecipes() {

    }

    private fun getIdsFavoriteRecipes() : String {

        return ""
    }

    private fun getIdsAddRecipes() : String {

        return ""
    }
}