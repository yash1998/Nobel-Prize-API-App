package com.example.nobelprizedummyapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PrizeFilter(var year: String? = null, var category: String? = null) : Parcelable