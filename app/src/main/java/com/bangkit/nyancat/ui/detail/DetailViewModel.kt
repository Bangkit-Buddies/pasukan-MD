package com.bangkit.nyancat.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.nyancat.data.ApiConfig
import com.bangkit.nyancat.data.response.DetailCatResponse
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {
    private val _detailData = MutableLiveData<DetailCatResponse>()
    val detailData: LiveData<DetailCatResponse> = _detailData
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> = _isError
    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> = _isFavorite

    fun setDetailData(catId: String) {
        // Set the data from the API
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailCat(catId)
        client.enqueue(object : Callback<DetailCatResponse> {
            override fun onResponse(call: Call<DetailCatResponse>, response: Response<DetailCatResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _detailData.value = response.body()
                    _isError.value = false
                } else {
                    _isError.value = true
                }
            }

            override fun onFailure(call: Call<DetailCatResponse>, t: Throwable) {
                _isLoading.value = false
                _isError.value = true
            }
        })
    }

    fun checkFavoriteCat(catId: String) {
        viewModelScope.launch {
            _isFavorite.value = true
        }
    }
}