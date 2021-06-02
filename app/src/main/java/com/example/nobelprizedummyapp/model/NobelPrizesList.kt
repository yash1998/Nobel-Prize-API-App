package com.example.nobelprizedummyapp.model

import com.google.gson.annotations.SerializedName

data class NobelPrizesList (
   @SerializedName("prizes") var prizes : List<Prizes>
)