package com.bangkit.nyancat.ui.favorite

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.nyancat.database.CatModel
import com.bangkit.nyancat.databinding.ActivityFavoriteListBinding
import com.bangkit.nyancat.ui.main.CatListAdapter

class FavoriteListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteListBinding
    private lateinit var adapter: FavoriteListAdapter
    private lateinit var rvFavCat: RecyclerView
    private lateinit var catModel: CatModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        catModel = ViewModelProvider(this).get(CatModel::class.java)


//        enableEdgeToEdge()

//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }


        rvFavCat = binding.rvFavCat
        rvFavCat.layoutManager = LinearLayoutManager(this)

        catModel.getFavoriteCat()?.observe(this, Observer { listCat ->
            adapter = FavoriteListAdapter(this, listCat)
            rvFavCat.adapter = adapter


        })


    }
}