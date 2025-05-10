package com.example.mealmeatapp.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel



class HomeViewModel : ViewModel() {
    val query = mutableStateOf("")

    fun onQueryChange(search: String) {
        query.value = search
    }

    fun onRandomRecipeClick() {
        // Lấy dữ liệu ngẫu nhiên từ API hoặc cơ sở dữ liệu
    }

}