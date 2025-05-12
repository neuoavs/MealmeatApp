package com.example.mealmeatapp.model

data class SettingItem(
    val iconResId: Int,
    val title: String,
    val onClick: () -> Unit
)
