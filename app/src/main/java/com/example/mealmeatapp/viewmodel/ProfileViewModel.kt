package com.example.mealmeatapp.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealmeatapp.apimodel.recipe.Recipe
import com.example.mealmeatapp.apimodel.request.ApiClient
import com.example.mealmeatapp.model.ProfileData
import com.example.mealmeatapp.model.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {
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

    val addedRecipe = mutableStateListOf<Recipe>()
    val favoriteRecipe = mutableStateListOf<Recipe>()
    val isUpdateProfile = mutableStateOf(false)

    val errorMessage = mutableStateOf<String?>(null)

    private val database = FirebaseDatabase.getInstance().reference

    fun reset() {
        email.value = ""
        password.value = ""
        isDiet.value = false
        gender.value = true
        age.value = 0
        heightCm.value = 203
        heightFeetInches.value = Pair(6, 8)
        heightUnit.value = "ft"
        weight.value = 71
        weightUnit.value = "kg"
        addedRecipe.clear()
        favoriteRecipe.clear()
        isUpdateProfile.value = false
        errorMessage.value = null
        Log.d("ProfileViewModel", "Profile reset")
    }

    fun updateAuth(user: User) {
        email.value = user.email
        password.value = user.password
        fetchProfile()
        fetchFavoriteRecipes()
        fetchAddedRecipes()
        updateProfileDatabase()
    }

    fun updateDiet(isDiet: Boolean) {
        this.isDiet.value = isDiet
        updateProfileDatabase()
    }

    fun updateGender(gender: Boolean) {
        this.gender.value = gender
        updateProfileDatabase()
    }

    fun updateAge(age: Int) {
        this.age.value = age
        updateProfileDatabase()
    }

    fun updateHeightCm(heightCm: Int) {
        this.heightCm.value = heightCm
        updateProfileDatabase()
    }

    fun updateHeightFeetInches(heightFeetInches: Pair<Int, Int>) {
        this.heightFeetInches.value = heightFeetInches
        updateProfileDatabase()
    }

    fun updateWeight(weight: Int) {
        this.weight.value = weight
        updateProfileDatabase()
    }

    fun updateWeightUnit(weightUnit: String) {
        this.weightUnit.value = weightUnit
        updateProfileDatabase()
    }

    fun updateHeightUnit(heightUnit: String) {
        this.heightUnit.value = heightUnit
        updateProfileDatabase()
    }

    fun mergeProfile(profileData: ProfileData) {
        isDiet.value = profileData.isDiet
        gender.value = profileData.gender
        age.value = profileData.age
        heightCm.value = profileData.heightCm
        heightFeetInches.value = profileData.heightFeetInches
        heightUnit.value = profileData.heightUnit
        weight.value = profileData.weight
        weightUnit.value = profileData.weightUnit
        updateProfileDatabase()
    }

    private fun updateProfileDatabase() {
        if (email.value.isNotEmpty()) {
            val profileData = mapOf(
                "email" to email.value,
                "password" to password.value,
                "isDiet" to isDiet.value,
                "gender" to gender.value,
                "age" to age.value,
                "heightCm" to heightCm.value,
                "heightFeetInches" to mapOf("feet" to heightFeetInches.value.first, "inches" to heightFeetInches.value.second),
                "heightUnit" to heightUnit.value,
                "weight" to weight.value,
                "weightUnit" to weightUnit.value
            )
            database.child("profile").orderByChild("email").equalTo(email.value)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists()) {
                            snapshot.children.first().ref.setValue(profileData)
                                .addOnSuccessListener {
                                    Log.d("ProfileViewModel", "Profile updated for ${email.value}")
                                    isUpdateProfile.value = true
                                }
                                .addOnFailureListener { e ->
                                    handleError("Update failed: ${e.message}")
                                }
                        } else {
                            database.child("profile").push().setValue(profileData)
                                .addOnSuccessListener {
                                    Log.d("ProfileViewModel", "Profile created for ${email.value}")
                                    isUpdateProfile.value = true
                                }
                                .addOnFailureListener { e ->
                                    handleError("Creation failed: ${e.message}")
                                }
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        handleError("Database error: ${error.message}")
                    }
                })
        } else {
            handleError("Email is empty")
        }
    }

    fun addRecipe(recipe: Recipe?) {
        recipe?.let {
            if (addedRecipe.none { r -> r.id == it.id }) {
                addedRecipe.add(it)
                val newEntry = mapOf("email" to email.value, "recipeId" to it.id)
                database.child("addedRecipes").push().setValue(newEntry)
                    .addOnFailureListener { e ->
                        handleError("Add recipe failed: ${e.message}")
                        addedRecipe.remove(it)
                    }
            }
        }
    }

    fun removeRecipe(recipe: Recipe?) {
        recipe?.let {
            if (addedRecipe.any { r -> r.id == it.id }) {
                addedRecipe.removeIf { r -> r.id == it.id }
                database.child("addedRecipes").orderByChild("recipeId").equalTo(it.id.toDouble())
                    .addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            snapshot.children.forEach { data ->
                                data.ref.removeValue()
                                    .addOnFailureListener { e ->
                                        handleError("Remove recipe failed: ${e.message}")
                                        addedRecipe.add(it)
                                    }
                            }
                        }
                        override fun onCancelled(error: DatabaseError) {
                            handleError("Database error: ${error.message}")
                            addedRecipe.add(it)
                        }
                    })
            }
        }
    }

    fun addFavoriteRecipe(recipe: Recipe?) {
        recipe?.let {
            if (favoriteRecipe.none { r -> r.id == it.id }) {
                favoriteRecipe.add(it)
                val newEntry = mapOf("email" to email.value, "recipeId" to it.id)
                database.child("favoriteRecipes").push().setValue(newEntry)
                    .addOnFailureListener { e ->
                        handleError("Add favorite failed: ${e.message}")
                        favoriteRecipe.remove(it)
                    }
            }
        }
    }

    fun removeFavoriteRecipe(recipe: Recipe?) {
        recipe?.let {
            if (favoriteRecipe.any { r -> r.id == it.id }) {
                favoriteRecipe.removeIf { r -> r.id == it.id }
                database.child("favoriteRecipes").orderByChild("recipeId").equalTo(it.id.toDouble())
                    .addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            snapshot.children.forEach { data ->
                                data.ref.removeValue()
                                    .addOnFailureListener { e ->
                                        handleError("Remove favorite failed: ${e.message}")
                                        favoriteRecipe.add(it)
                                    }
                            }
                        }
                        override fun onCancelled(error: DatabaseError) {
                            handleError("Database error: ${error.message}")
                            favoriteRecipe.add(it)
                        }
                    })
            }
        }
    }

    fun fetchFavoriteRecipes() {
        if (email.value.isNotEmpty()) {
            database.child("favoriteRecipes").orderByChild("email").equalTo(email.value)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val recipeIds = snapshot.children.mapNotNull { it.child("recipeId").getValue(Int::class.java) }
                            .joinToString(",")
                        if (recipeIds.isNotEmpty()) {
                            viewModelScope.launch {
                                try {
                                    val recipes = ApiClient.apiService.getRecipesInformationBulk(ids = recipeIds)
                                    favoriteRecipe.clear()
                                    favoriteRecipe.addAll(recipes)
                                    Log.d("ProfileViewModel", "Fetched ${recipes.size} favorites")
                                } catch (e: Exception) {
                                    handleError("Fetch favorites failed: ${e.message}")
                                }
                            }
                        } else {
                            favoriteRecipe.clear()
                            Log.d("ProfileViewModel", "No favorites found")
                        }
                    }
                    override fun onCancelled(error: DatabaseError) {
                        handleError("Fetch favorites cancelled: ${error.message}")
                    }
                })
        }
    }

    fun fetchAddedRecipes() {
        if (email.value.isNotEmpty()) {
            database.child("addedRecipes").orderByChild("email").equalTo(email.value)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val recipeIds = snapshot.children.mapNotNull { it.child("recipeId").getValue(Int::class.java) }
                            .joinToString(",")
                        if (recipeIds.isNotEmpty()) {
                            viewModelScope.launch {
                                try {
                                    val recipes = ApiClient.apiService.getRecipesInformationBulk(ids = recipeIds)
                                    addedRecipe.clear()
                                    addedRecipe.addAll(recipes)
                                    Log.d("ProfileViewModel", "Fetched ${recipes.size} added recipes")
                                } catch (e: Exception) {
                                    handleError("Fetch added recipes failed: ${e.message}")
                                }
                            }
                        } else {
                            addedRecipe.clear()
                            Log.d("ProfileViewModel", "No added recipes found")
                        }
                    }
                    override fun onCancelled(error: DatabaseError) {
                        handleError("Fetch added recipes cancelled: ${error.message}")
                    }
                })
        }
    }

    fun fetchProfile() {
        if (email.value.isNotEmpty()) {
            database.child("profile").orderByChild("email").equalTo(email.value)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists()) {
                            snapshot.children.firstOrNull()?.let { data ->
                                try {
                                    // Truy cập trực tiếp các trường từ DataSnapshot
                                    val heightFeetInchesSnapshot = data.child("heightFeetInches")
                                    mergeProfile(
                                        ProfileData(
                                            isDiet = data.child("isDiet").getValue(Boolean::class.java) ?: false,
                                            gender = data.child("gender").getValue(Boolean::class.java) ?: true,
                                            age = data.child("age").getValue(Long::class.java)?.toInt() ?: 0,
                                            heightCm = data.child("heightCm").getValue(Long::class.java)?.toInt() ?: 203,
                                            heightFeetInches = Pair(
                                                heightFeetInchesSnapshot.child("feet").getValue(Long::class.java)?.toInt() ?: 6,
                                                heightFeetInchesSnapshot.child("inches").getValue(Long::class.java)?.toInt() ?: 8
                                            ),
                                            heightUnit = data.child("heightUnit").getValue(String::class.java) ?: "ft",
                                            weight = data.child("weight").getValue(Long::class.java)?.toInt() ?: 71,
                                            weightUnit = data.child("weightUnit").getValue(String::class.java) ?: "kg"
                                        )
                                    )
                                    Log.d("ProfileViewModel", "Profile loaded for ${email.value}")
                                } catch (e: Exception) {
                                    handleError("Error parsing profile data: ${e.message}")
                                }
                            } ?: run {
                                Log.w("ProfileViewModel", "No profile data for ${email.value}")
                            }
                        } else {
                            Log.w("ProfileViewModel", "No profile found for ${email.value}")
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        handleError("Fetch profile cancelled: ${error.message}")
                    }
                })
        } else {
            Log.w("ProfileViewModel", "Email empty, skipping fetch")
        }
    }

    private fun getIdsFavoriteRecipes(): String {
        return favoriteRecipe.map { it.id.toString() }.joinToString(",")
    }

    private fun getIdsAddRecipes(): String {
        return addedRecipe.map { it.id.toString() }.joinToString(",")
    }

    private fun handleError(message: String) {
        errorMessage.value = message
        Log.e("ProfileViewModel", message)
    }
}