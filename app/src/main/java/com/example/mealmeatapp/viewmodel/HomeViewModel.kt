package com.example.mealmeatapp.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.mealmeatapp.apimodel.recipe.Recipe
import com.example.mealmeatapp.apimodel.request.ApiClient
import com.example.mealmeatapp.apimodel.result.RandomRecipesResponse


class HomeViewModel : ViewModel() {
    val query = mutableStateOf("")
    val selectedCategory = mutableStateOf("random")
    val recipes = mutableStateOf(emptyList<Recipe>())
    val categories = listOf<String>(
        "Random",
        "Breakfast",
        "Main course",
        "Salad",
        "Desert"
    )

    init {
        fetchRandomRecipes() // Tự động gọi API khi ViewModel khởi tạo
    }

    fun onQueryChange(search: String) {
        query.value = search
    }

    fun onCategoryChange(category: String) {
        selectedCategory.value = category

        if (category == "Random") {
            fetchRandomRecipes()
        }
    }

    fun fetchRandomRecipes() {
        ApiClient.apiService.getRamdomRepice().enqueue(object : retrofit2.Callback<RandomRecipesResponse> {
            override fun onResponse(
                call: retrofit2.Call<RandomRecipesResponse>,
                response: retrofit2.Response<RandomRecipesResponse>
            ) {
                if (response.isSuccessful) {
                    recipes.value = response.body()?.recipes ?: emptyList()
                } else {
                    // Log lỗi nếu cần
                    println("Lỗi response: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: retrofit2.Call<RandomRecipesResponse>, t: Throwable) {
                // Log lỗi nếu cần
                println("Lỗi kết nối: ${t.localizedMessage}")
            }
        })
    }
}