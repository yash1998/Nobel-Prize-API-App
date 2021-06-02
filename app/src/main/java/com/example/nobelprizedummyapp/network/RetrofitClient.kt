package com.example.nobelprizedummyapp.network

import com.example.nobelprizedummyapp.api.NobelPrizeAPI
import com.example.nobelprizedummyapp.constants.GlobalConstants.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private var instance: NobelPrizeAPI? = null

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getInstance(): NobelPrizeAPI? {
        if (instance == null) {
            instance = retrofit.create(
                NobelPrizeAPI::class.java
            )
        }
        return instance
    }
}