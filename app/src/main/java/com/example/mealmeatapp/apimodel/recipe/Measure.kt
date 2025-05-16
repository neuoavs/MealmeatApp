package com.example.mealmeatapp.apimodel.recipe

import com.google.gson.annotations.SerializedName


data class Measure (

  @SerializedName("us"     ) var us     : Us?     = Us(),
  @SerializedName("metric" ) var metric : Metric? = Metric()

)