package com.bangkit.nyancat.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import com.bangkit.nyancat.R
import com.bangkit.nyancat.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    private val viewModel by viewModels<DetailViewModel>()


    private var isChecked: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val catId = intent.getStringExtra(EXTRA_CAT_ID)
        val name = intent.getStringExtra(EXTRA_CAT_NAME)
        val avatar_url = intent.getStringExtra(EXTRA_CAT_AVATAR)


        if (catId != null) {
            viewModel.setDetailData(catId ?: "")

        } else
        {
            Log.e("DetailActivity", "Tidak ada data")
        }

        viewModel.isFavorite.observe(this, Observer { isFavorite ->
            binding.tbFavoriteButton.isChecked = isFavorite
        })

        viewModel.checkCat(catId?:"")

        binding.tbFavoriteButton.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked){
                    Log.d("DetailActivity", "Toggle button checked: $isChecked")
                    viewModel.addFavoriteCat(name = name?: "", id = catId?:"", avatar_url = "https://cdn2.thecatapi.com/images/${avatar_url?: ""}.jpg")
                }
                else {
                    catId?.let {
                        viewModel.deleteFavoriteCat(it)
                    }
                }

        }
        initView()
        observeData()

    }


    private fun initView() {
        binding.apply {
            tbDetail.setNavigationOnClickListener {
                OnBackPressedDispatcher().onBackPressed()

            }

        }
    }

    private fun observeData() {
        viewModel.isError.observe(this) {
            binding.tvError.visibility = if (it) View.VISIBLE else View.GONE
        }
        viewModel.isLoading.observe(this) {
            binding.pbDetail.visibility = if (it) View.VISIBLE else View.GONE
        }
        viewModel.isFavorite.observe(this) { isFavorite ->
            binding.apply {
                // Set the favorite state
//                favoriteButton.isChecked = isFavorite
            }
        }
        viewModel.detailData.observe(this) { detailData ->

            binding.apply {

                // Set the data to the view
            }
        }
    }

    companion object {
        const val EXTRA_CAT_ID = "extra_cat_id"
        const val EXTRA_CAT_NAME = "extra_cat_name"
        const val EXTRA_CAT_AVATAR = "extra_cat_avatar"
    }
}