package com.bangkit.nyancat.data

import com.bangkit.nyancat.data.response.DetailCatResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("images/{catId}")
    fun getDetailCat(
        @Path("catId") catId: String
    ): Call<DetailCatResponse>
}