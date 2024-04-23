package com.bangkit.nyancat.ui.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bangkit.nyancat.data.ApiConfig
import com.bangkit.nyancat.data.response.SearchCatResponse
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CatViewModel(application: Application) : AndroidViewModel(application){
    
    private val _catList = MutableLiveData<List<SearchCatResponse>>()
    val catList: LiveData<List<SearchCatResponse>> = _catList
    
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    
    fun searchCat(query: String){
        _isLoading.value = true
        viewModelScope.launch {
            val client = ApiConfig.getApiService().getCatList(query)
            client.enqueue(object : Callback<List<SearchCatResponse>> {
                override fun onResponse(
                    call: Call<List<SearchCatResponse>>,
                    response: Response<List<SearchCatResponse>>
                ) {
                    _isLoading.value = false
                    if (response.isSuccessful) {
                        response.body()?. let { _catList.value = response.body() }
                    } else {
                        Log.d("searchCat FailResponse", response.message())
                    }
                }
                override fun onFailure(call: Call<List<SearchCatResponse>>, t: Throwable) {
                    _isLoading.value = false
                    Log.d("searchCat onFailure",t.message?:"unknown error")
                }
            })
        }
    }
}