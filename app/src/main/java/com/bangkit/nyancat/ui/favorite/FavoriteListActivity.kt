package com.bangkit.nyancat.ui.favorite

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.nyancat.R
import com.bangkit.nyancat.database.Cat
import com.bangkit.nyancat.database.CatModel
import com.bangkit.nyancat.databinding.ActivityFavoriteListBinding
import com.bangkit.nyancat.ui.detail.DetailActivity
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


        enableEdgeToEdge()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.tbFavorite.setNavigationOnClickListener {
            this@FavoriteListActivity.onBackPressedDispatcher.onBackPressed()
        }


        rvFavCat = binding.rvFavCat
        rvFavCat.layoutManager = LinearLayoutManager(this)

        catModel.getFavoriteCat()?.observe(this, Observer { listCat ->
            adapter = FavoriteListAdapter(this, listCat)
            rvFavCat.adapter = adapter
            adapter.setOnItemClickCallback(object : FavoriteListAdapter.OnItemClickCallback {
                override fun onItemClicked(cat: Cat) {
                    Log.d("FavoriteListAdapter", "Item clicked: ${cat.name}, ${cat.avatar_url}")
                    Intent(this@FavoriteListActivity, DetailActivity::class.java).apply {
                        putExtra(DetailActivity.EXTRA_CAT_ID, cat.id)
                        putExtra(DetailActivity.EXTRA_CAT_NAME, cat.name)
                        putExtra(DetailActivity.EXTRA_CAT_AVATAR, cat.avatar_url)
                        startActivity(this)
                    }
                }

            })


        })


    }
}