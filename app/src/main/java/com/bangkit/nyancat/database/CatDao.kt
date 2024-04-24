package com.bangkit.nyancat.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CatDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFavoriteCat(cat: Cat)

    @Query("SELECT * FROM favorite_cat")
    fun getFavoriteCat(): LiveData<List<Cat>>

    @Query("SELECT count(*) FROM favorite_cat WHERE favorite_cat.id = :id")
    suspend fun checkUser(id: Int): Int

    @Query("DELETE FROM favorite_cat WHERE favorite_cat.id = :id")
    suspend fun deleteFavoriteCat(id: Int): Int
}