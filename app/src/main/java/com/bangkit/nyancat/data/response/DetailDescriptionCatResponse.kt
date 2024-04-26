package com.bangkit.nyancat.data.response

import com.google.gson.annotations.SerializedName

data class DetailDescriptionCatResponse(

	@field:SerializedName("width")
	val width: Int? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("url")
	val url: String? = null,

	@field:SerializedName("breeds")
	val breeds: List<BreedsDescriptionItem?>? = null,

	@field:SerializedName("height")
	val height: Int? = null
)

data class BreedsDescriptionItem(

	@field:SerializedName("wikipedia_url")
	val wikipediaUrl: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("reference_image_id")
	val referenceImageId: String? = null,
)
