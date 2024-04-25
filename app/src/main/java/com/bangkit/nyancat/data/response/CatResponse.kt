package com.bangkit.nyancat.data.response

import com.google.gson.annotations.SerializedName

data class CatResponse(
    @field:SerializedName("weight")
    val weight: WeightCat? = null,

    @field:SerializedName("id")
    val id: String? = null,

    @field: SerializedName("name")
    val name: String?= null,

    @field: SerializedName("cfa_url")
    val cfa_url: String?=null,

    @field: SerializedName("vetstreet_url")
    val vetstreet_url: String?=null,

    @field: SerializedName("vcahospitals_url")
    val vcahospital_url: String?= null,

    @field: SerializedName("temprament")
    val temprament: String?=null,

    @field: SerializedName("origin")
    val origin: String?=null,

    @field: SerializedName("country_codes")
    val country_codes: String?=null,

    @field: SerializedName("country_code")
    val country_code: String?=null,

    @field: SerializedName("description")
    val description: String?=null,

    @field: SerializedName("life_span")
    val life_span: String?=null,

    @field: SerializedName("indoor")
    val indoor: Int?=null,

    @field: SerializedName("lap")
    val lap: Int?=null,

    @field: SerializedName("alt_names")
    val alt_names: String?=null,

    @field: SerializedName("adaptability")
    val adaptability: Int?=null,

    @field: SerializedName("affection_level")
    val affection_level: Int?=null,

    @field: SerializedName("child_friendly")
    val child_friendly: Int?=null,

    @field: SerializedName("dog_friendly")
    val dog_friendly: Int?=null,

    @field: SerializedName("energy_level")
    val energy_level: Int?=null,

    @field: SerializedName("grooming")
    val grooming: Int?=null,

    @field: SerializedName("health_issues")
    val health_issues: Int?=null,

    @field: SerializedName("intelligence")
    val intelligence: Int?=null,

    @field: SerializedName("shedding_level")
    val shedding_level: Int?=null,

    @field: SerializedName("social_needs")
    val social_needs: Int?=null,

    @field: SerializedName("stranger_friendly")
    val stranger_friendly: Int?=null,

    @field: SerializedName("vocalisation")
    val vocalisation: Int?=null,

    @field: SerializedName("experimental")
    val experimental: Int?=null,

    @field: SerializedName("hairless")
    val hairless: Int?=null,

    @field: SerializedName("natural")
    val natural: Int?=null,

    @field: SerializedName("rare")
    val rare: Int?=null,

    @field: SerializedName("rex")
    val rex: Int?=null,

    @field: SerializedName("suppressed_tail")
    val suppressed_tail: Int?=null,

    @field: SerializedName("short_legs")
    val short_legs: Int?=null,

    @field: SerializedName("wikipedia_url")
    val wikipedia_url: String?=null,

    @field: SerializedName("hypoallergenic")
    val hypoallergenic: Int?=null,

    @field: SerializedName("reference_image_id")
    val reference_image_id: String?=null,
)

data class WeightCat(
    @field: SerializedName("imperial")
    val imperial: String?=null,

    @field: SerializedName("metric")
    val metric: String?=null
)