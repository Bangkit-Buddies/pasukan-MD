package com.bangkit.nyancat.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Cat::class], version = 1)
abstract class CatDatabase: RoomDatabase() {
    abstract fun catDao(): CatDao

    companion object{
        @Volatile
        var INSTANCE : CatDatabase? = null
        @JvmStatic
        fun getDatabase(context: Context): CatDatabase?{
            if (INSTANCE==null){
                synchronized(CatDatabase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext, CatDatabase::class.java, "database-cat")
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE
        }
    }
}