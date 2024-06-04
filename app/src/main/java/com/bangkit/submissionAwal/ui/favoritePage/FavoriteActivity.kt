package com.bangkit.submissionAwal.ui.favoritePage

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.submissionAwal.databinding.ActivityFavoriteBinding
import com.bangkit.submissionAwal.ui.adapter.FavoriteAdapter
import com.bangkit.submissionAwal.ui.detailPage.DetailActivity

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding : ActivityFavoriteBinding
    private lateinit var listFavAdapter : FavoriteAdapter
    private lateinit var favViewModel: FavoriteViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val layoutManager = LinearLayoutManager(this)
        binding.rvFavorite.layoutManager = layoutManager
        listFavAdapter = FavoriteAdapter()
        binding.rvFavorite.adapter = listFavAdapter



        val viewModelFactory = FavoriteViewModelFactory(application)
        favViewModel = ViewModelProvider(this, viewModelFactory)[FavoriteViewModel::class.java]

        favViewModel.getFavUser().observe(this){data ->
            listFavAdapter.setOnItemClickListener { item ->
                val username = item?.username
                val userId = item?.id
                val intent = Intent(this@FavoriteActivity, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_USER, username)
                intent.putExtra("EXTRA_ID", userId)
                startActivity(intent)
            }
            listFavAdapter.submitList(data)

            if (data.isNullOrEmpty()){
                binding.tvNoFavorite.visibility = View.VISIBLE
            }else{
                binding.tvNoFavorite.visibility = View.GONE
            }
        }
    }
}