package com.bangkit.nyancat.ui.main

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.nyancat.R
import com.bangkit.nyancat.data.response.SearchCatResponse
import com.bangkit.nyancat.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var rvCat: RecyclerView
    private lateinit var catQuery: String
    private val viewModel by viewModels<CatViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        binding.appBar.setOnMenuItemClickListener { menuItem ->
            when(menuItem.itemId) {
                R.id.menu_change_theme -> {
//                    val intent = Intent(this, ChangeThemeActivity::class.java)
//                    startActivity(intent)
                    true
                }
                
                R.id.menu_favourite_list -> {
//                    val intent = Intent(this, FavouriteActivity::class.java)
//                    startActivity(intent)
                    true
                }
                
                else -> false
            }
        }
        
        rvCat = binding.rvCat
        rvCat.layoutManager = LinearLayoutManager(this)
        
        viewModel.isLoading.observe(this){ isLoading ->
            if (isLoading) {
                showLoading(true)
            }
            else {
                showLoading(false)
            }
        }
        
        viewModel.catList.observe(this){ catList ->
            val adapter = CatListAdapter(this, catList)
            Log.d("observeCat",catList.toString())
            binding.rvCat.adapter = adapter
            adapter.setOnItemClickCallback(object: CatListAdapter.OnItemClickCallback{
                override fun onItemClicked(catDetail: SearchCatResponse) {
                    TODO("Not yet implemented")
                }
            })
        }
        
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = binding.searchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                catQuery = query
                viewModel.searchCat(catQuery)
                searchView.clearFocus()
                return true
            }
            
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        
    }
    
    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}