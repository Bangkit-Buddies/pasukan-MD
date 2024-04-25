package com.bangkit.nyancat.ui.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.nyancat.data.ApiConfig
import com.bangkit.nyancat.data.response.DetailCatResponse
import com.bangkit.nyancat.data.response.SearchCatResponse
import com.bangkit.nyancat.database.Cat
import com.bangkit.nyancat.database.CatDao
import com.bangkit.nyancat.database.CatDatabase
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application) : AndroidViewModel(application) {
    private val _detailData = MutableLiveData<DetailCatResponse>()
    val detailData: LiveData<DetailCatResponse> = _detailData
    private val _searchData = MutableLiveData<SearchCatResponse>()
    val searchData: LiveData<SearchCatResponse> = _searchData
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> = _isError
    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> = _isFavorite

    private val catDao: CatDao?
    private val catDatabase: CatDatabase?

    init {
        catDatabase = CatDatabase.getDatabase(application)
        catDao = catDatabase?.catDao()
    }


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
            val isFavorite = catDao?.checkUser(catId.toInt())?: 0>0
            _isFavorite.value = isFavorite

            if (isFavorite){
                catDao?.deleteFavoriteCat(catId.toInt())
            } else {
                val cat = mapSearchCatResponseToCat(searchData.value!!)
                catDao?.addFavoriteCat(cat)
            }
        }


    }
    private fun mapSearchCatResponseToCat(searchCatResponse: SearchCatResponse): Cat {
        return Cat(
            name = searchCatResponse.name?:"",
            id = searchCatResponse.id?:"",
            avatar_url = "https://cdn2.thecatapi.com/images/${searchCatResponse.referenceImageId ?: ""}.jpg"
        )
    }
    }



