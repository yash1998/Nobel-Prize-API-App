package com.example.nobelprizedummyapp.model

import com.google.gson.annotations.SerializedName

data class Prizes(
    @SerializedName("year") var year: String,
    @SerializedName("category") var category: String,
    @SerializedName("laureates") var laureates: List<Laureates>?
)