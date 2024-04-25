package com.bangkit.nyancat.ui.detail

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bangkit.nyancat.R
import com.bangkit.nyancat.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    private val viewModel by viewModels<DetailViewModel>()

    private var catId: String? = null

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

        catId = intent.getStringExtra(EXTRA_CAT_ID)

        if (catId != null) {
            viewModel.setDetailData(catId ?: "")
            viewModel.checkFavoriteCat(catId ?: "")
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
                favoriteButton.isChecked = isFavorite
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
    }
}