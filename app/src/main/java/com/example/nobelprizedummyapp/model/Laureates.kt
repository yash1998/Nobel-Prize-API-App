package com.example.nobelprizedummyapp.model

import com.google.gson.annotations.SerializedName

data class Laureates(
    @SerializedName("id") var id: String,
    @SerializedName("firstname") var firstname: String,
    @SerializedName("surname") var surname: String,
    @SerializedName("motivation") var motivation: String,
    @SerializedName("share") var share: String
)