package com.example.mealmeatapp.apimodel.ingredient

import com.google.gson.annotations.SerializedName

data class EstimatedCost(
    @SerializedName("value" ) var value : Double? = null,
    @SerializedName("unit"  ) var unit  : String? = null
)
