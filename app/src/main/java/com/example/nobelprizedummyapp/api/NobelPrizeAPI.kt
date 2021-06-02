package com.example.nobelprizedummyapp.api

import com.example.nobelprizedummyapp.model.NobelPrizesList
import retrofit2.Call
import retrofit2.http.GET

interface NobelPrizeAPI {

    @GET("v1/prize.json")
    fun getNobelPrizesList(): Call<NobelPrizesList>
}