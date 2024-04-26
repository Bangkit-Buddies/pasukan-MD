package com.bangkit.nyancat.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.bangkit.nyancat.R
import com.bangkit.nyancat.databinding.ActivityDetailBinding
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    private val viewModel by viewModels<DetailViewModel>()

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
            viewModel.setDetailData(avatar_url ?: "")

        } else {
            Log.e("DetailActivity", "Tidak ada data")
        }

        viewModel.isFavorite.observe(this, Observer { isFavorite ->
            binding.tbFavoriteButton.isChecked = isFavorite
        })

        viewModel.checkCat(catId ?: "")

        binding.tbFavoriteButton.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                Log.d("DetailActivity", "Toggle button checked: $isChecked")
                viewModel.addFavoriteCat(
                    name = name ?: "",
                    id = catId ?: "",
                    avatar_url = avatar_url ?: ""
                )
                Toast.makeText(this, "Added to Favorite! its so cute :)", Toast.LENGTH_SHORT).show()
            } else {
                catId?.let {
                    viewModel.deleteFavoriteCat(it)
                }
                Toast.makeText(this, "Deleted from Favorite :(", Toast.LENGTH_SHORT).show()
            }

        }

        setTabSection()
        initListener()
        observeData()
    }

    private fun observeData() {
        viewModel.isError.observe(this) {
            if (it) {
                binding.tvError.visibility =  View.VISIBLE
                binding.viewDetailContent.isVisible = false
            } else {
                binding.tvError.visibility =  View.GONE
                binding.viewDetailContent.isVisible = true
            }
        }
        viewModel.isLoading.observe(this) {
            if (it) {
                binding.pbDetail.visibility = View.VISIBLE
                binding.viewDetailContent.isVisible = false
            } else {
                binding.pbDetail.visibility = View.GONE
                binding.viewDetailContent.isVisible = true
            }
        }
        viewModel.isFavorite.observe(this) { binding.tbFavoriteButton.isChecked = it }
        viewModel.detailData.observe(this) { detailData ->
            binding.apply {
                binding.tbDetail.title = detailData.breeds?.first()?.name ?: "Detail Activity"
                Glide.with(this@DetailActivity)
                    .load(detailData.url)
                    .into(ivCatDetail)
            }
        }
    }

    private fun initListener() {
        binding.apply {
            tbDetail.setNavigationOnClickListener {
                this@DetailActivity.onBackPressedDispatcher.onBackPressed()
            }
        }
    }

    private fun setTabSection() {
        val tabPagerAdapter = TabAdapter(this)
        binding.viewPager2.adapter = tabPagerAdapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager2) { tab, position ->
            tab.text = TAB_TITLES[position]
        }.attach()

    }

    companion object {
        const val EXTRA_CAT_ID = "extra_cat_id"
        const val EXTRA_CAT_NAME = "extra_cat_name"
        const val EXTRA_CAT_AVATAR = "extra_cat_avatar"

        private val TAB_TITLES = arrayOf(
            "Profile",
            "Description"
        )
    }
}