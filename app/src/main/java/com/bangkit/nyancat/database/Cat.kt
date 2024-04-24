package com.bangkit.nyancat.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "favorite_cat")
data class Cat (
    val name: String,

    @PrimaryKey
    val id: String,

    val avatar_url: String
): Serializable