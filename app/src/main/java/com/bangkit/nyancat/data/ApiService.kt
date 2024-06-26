package com.bangkit.nyancat.data

import com.bangkit.nyancat.data.response.CatResponse
import com.bangkit.nyancat.data.response.DetailCatResponse
import com.bangkit.nyancat.data.response.DetailDescriptionCatResponse
import com.bangkit.nyancat.data.response.DetailProfileCatResponse
import com.bangkit.nyancat.data.response.SearchCatResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("images/{catId}")
    fun getDetailCat(
        @Path("catId") catId: String
    ): Call<DetailCatResponse>

    @GET("images/{catId}")
    fun getDetailDescriptionCat(
        @Path("catId") catId: String
    ): Call<DetailDescriptionCatResponse>

    @GET("images/{catId}")
    fun getDetailProfileCat(
        @Path("catId") catId: String
    ): Call<DetailProfileCatResponse>
    
    @GET("breeds/search?attach_image=0")
    fun getCatList(
        @Query("q") query: String
    ): Call<List<SearchCatResponse>>

    @GET("breeds/{breed_id}")
    fun getCatInfoById(
        @Path("breed_id") breedId: String
    ): Call<CatResponse>
}