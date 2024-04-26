package com.bangkit.nyancat.data.response

import com.google.gson.annotations.SerializedName

data class DetailCatResponse(

	@field:SerializedName("width")
	val width: Int? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("url")
	val url: String? = null,

	@field:SerializedName("height")
	val height: Int? = null,

	@field:SerializedName("breeds")
	val breeds: List<DetailCatBreeds?>? = null,
)

data class DetailCatBreeds(
	@field:SerializedName("name")
	val name: String? = null,
)
