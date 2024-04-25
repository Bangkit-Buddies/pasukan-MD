package com.bangkit.nyancat.database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class CatModel(application: Application): AndroidViewModel(application) {
    private var catDao: CatDao?
    private var catDatabase: CatDatabase?

    init {
        catDatabase = CatDatabase.getDatabase(application)
        catDao = catDatabase?.catDao()

    }

    fun getFavoriteCat(): LiveData<List<Cat>>?{
        return catDao?.getFavoriteCat()
    }
}