package com.example.nobelprizedummyapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nobelprizedummyapp.constants.GlobalConstants.TAG
import com.example.nobelprizedummyapp.model.NobelPrizesList
import com.example.nobelprizedummyapp.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NobelPrizesViewModel : ViewModel() {

    private val retrofitClient by lazy { RetrofitClient.getInstance() }
    val nobelPrizesList: MutableLiveData<NobelPrizesList> = MutableLiveData()
    val isLoading: MutableLiveData<Boolean> = MutableLiveData(true)

    fun getNobelPrizesList() {
        val call = retrofitClient?.getNobelPrizesList()
        call?.enqueue(object : Callback<NobelPrizesList> {
            override fun onFailure(call: Call<NobelPrizesList>, t: Throwable) {
                Log.d(TAG, t.printStackTrace().toString())
            }

            override fun onResponse(
                call: Call<NobelPrizesList>,
                response: Response<NobelPrizesList>
            ) {
                if (response.isSuccessful) {
                    nobelPrizesList.value = response.body()
                }
            }
        })
    }
}