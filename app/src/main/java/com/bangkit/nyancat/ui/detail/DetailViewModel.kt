package com.bangkit.nyancat.ui.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.nyancat.data.ApiConfig
import com.bangkit.nyancat.data.response.CatResponse
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
    private val _catData = MutableLiveData<CatResponse>()
    val catData: LiveData<CatResponse> = _catData
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


        fun getCatData(catId: String) {
            val client = ApiConfig.getApiService().getCatInfoById(catId)
            client.enqueue(object : Callback<CatResponse> {
                override fun onResponse(call: Call<CatResponse>, response: Response<CatResponse>) {
                    if (response.isSuccessful) {
                        _catData.value = response.body()
                    } else {
                        Log.e("DetailViewModel", "Failed to get cat data: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<CatResponse>, t: Throwable) {
                    Log.e("DetailViewModel", "Failed to get cat data: ${t.message}")
                }
            })
        }

        fun deleteFavoriteCat(id: String){
       viewModelScope.launch {
           catDao?.deleteFavoriteCat(id)
       }
   }

    fun addFavoriteCat(name: String, id: String, avatar_url: String){
        viewModelScope.launch {
            try {
                val cat = Cat(name, id, avatar_url)
                catDao?.addFavoriteCat(cat)
                Log.d("DetailViewModel", "Kucing favorite berhasil ditambahkan dengan id: $id")
            } catch(e: Exception){
                Log.e("DetailViewModel", "Failed to add favorite dengan id: $id")
            }

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




