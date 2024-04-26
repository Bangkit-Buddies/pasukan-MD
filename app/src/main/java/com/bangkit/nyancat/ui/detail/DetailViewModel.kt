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
import com.bangkit.nyancat.data.response.DetailDescriptionCatResponse
import com.bangkit.nyancat.data.response.DetailProfileCatResponse
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

    private val _descriptionData = MutableLiveData<DetailDescriptionCatResponse>()
    val descriptionData: LiveData<DetailDescriptionCatResponse> = _descriptionData
    private val _isDescriptionLoading = MutableLiveData<Boolean>()
    val isDescriptionLoading: LiveData<Boolean> = _isDescriptionLoading
    private val _isDescriptionError = MutableLiveData<Boolean>()
    val isDescriptionError: LiveData<Boolean> = _isDescriptionError

    private val _profileData = MutableLiveData<DetailProfileCatResponse>()
    val profileData: LiveData<DetailProfileCatResponse> = _profileData
    private val _isProfileLoading = MutableLiveData<Boolean>()
    val isProfileLoading: LiveData<Boolean> = _isProfileLoading
    private val _isProfileError = MutableLiveData<Boolean>()
    val isProfileError: LiveData<Boolean> = _isProfileError

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

    fun checkCat(id: String) = viewModelScope.launch{
        val isFavorite = catDao?.checkCat(id)?: 0>0
        _isFavorite.value = isFavorite
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

    fun setDetailDescriptionData(catId: String) {
        // Set the data from the API
        _isDescriptionLoading.value = true
        val client = ApiConfig.getApiService().getDetailDescriptionCat(catId)
        client.enqueue(object : Callback<DetailDescriptionCatResponse> {
            override fun onResponse(call: Call<DetailDescriptionCatResponse>, response: Response<DetailDescriptionCatResponse>) {
                _isDescriptionLoading.value = false
                if (response.isSuccessful) {
                    _descriptionData.value = response.body()
                    _isDescriptionError.value = false
                } else {
                    _isDescriptionError.value = true
                }
            }

            override fun onFailure(call: Call<DetailDescriptionCatResponse>, t: Throwable) {
                _isDescriptionLoading.value = false
                _isDescriptionError.value = true
            }
        })
    }

    fun setDetailProfileData(catId: String) {
        // Set the data from the API
        _isProfileLoading.value = true
        val client = ApiConfig.getApiService().getDetailProfileCat(catId)
        client.enqueue(object : Callback<DetailProfileCatResponse> {
            override fun onResponse(call: Call<DetailProfileCatResponse>, response: Response<DetailProfileCatResponse>) {
                _isProfileLoading.value = false
                if (response.isSuccessful) {
                    _profileData.value = response.body()
                    _isProfileError.value = false
                } else {
                    _isProfileError.value = true
                }
            }

            override fun onFailure(call: Call<DetailProfileCatResponse>, t: Throwable) {
                _isProfileLoading.value = false
                _isProfileError.value = true
            }
        })
    }




    }
    private fun mapSearchCatResponseToCat(searchCatResponse: SearchCatResponse): Cat {
        return Cat(
            name = searchCatResponse.name?:"",
            id = searchCatResponse.id?:"",
            avatar_url = "https://cdn2.thecatapi.com/images/${searchCatResponse.referenceImageId ?: ""}.jpg"
        )
    }




