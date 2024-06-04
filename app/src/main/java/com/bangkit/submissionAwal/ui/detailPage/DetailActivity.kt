package com.bangkit.submissionAwal.ui.detailPage

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bangkit.submissionAwal.R
import com.bangkit.submissionAwal.data.local.entity.FavoritEntity
import com.bangkit.submissionAwal.databinding.ActivityDetailBinding
import com.bangkit.submissionAwal.ui.adapter.SectionsPagerAdapter
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {
    // Properties
    private lateinit var detailViewModel: DetailViewModel
    private lateinit var detailBinding: ActivityDetailBinding
    private var userId: Int = 0
    private lateinit var avatarURl: String
    private var isFavorite = false

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.follower,
            R.string.following
        )
        const val EXTRA_USER = "extra_user"
        lateinit var username: String

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailBinding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(detailBinding.root)

        // Inisialisasi View Model
        detailViewModel = ViewModelProvider(this)[DetailViewModel::class.java]

        // Mendapatkan parcel string
        username = intent.getStringExtra(EXTRA_USER).toString()
        userId = intent.getIntExtra("EXTRA_ID", userId)


//        userId = detailViewModel.userDetail.value?.id ?: 0


        setupUI()
        setupObservers()
    }

    private fun setupUI() {
        // Set onClickListener untuk tombol favorit
        detailBinding.favButton.setOnClickListener {
            isFavorite = !isFavorite
            updateFavoriteButton()
            manageFavorite()
        }

        // Set adapter untuk ViewPager2
        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        val viewPager: ViewPager2 = findViewById(R.id.viewPager2)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f
    }

    private fun manageFavorite() {
        avatarURl = detailViewModel.userDetail.value?.avatarUrl.toString()
        val favoritEntity = FavoritEntity(
            id = userId,
            username = username,
            avatarUrl = avatarURl,
        )

        if (isFavorite) {
            detailViewModel.addFav(favoritEntity)
        } else {
            detailViewModel.removeFav(favoritEntity)
        }
    }


    private fun setupObservers() {
        // Observing data dari ViewModel
        detailViewModel.getUserDetail(username)
        detailViewModel.userDetail.observe(this) { userDetail ->
            userDetail?.let {
                detailBinding.tvUsername.text = it.login
                detailBinding.tvFolowers.text = it.followers.toString()
                detailBinding.tvFolowing.text = it.following.toString()
                detailBinding.tvRepos.text = it.publicRepos.toString()
                detailBinding.tvName.text = it.name
                Glide.with(this@DetailActivity)
                    .load(it.avatarUrl)
                    .circleCrop()
                    .into(detailBinding.profileImage)
            }
        }

        detailViewModel.toastErrorDetail.observe(this) { error ->
            Toast.makeText(this@DetailActivity, error.toString(), Toast.LENGTH_SHORT).show()
        }

        detailViewModel.isLoading.observe(this) { isLoading ->
            showLoading(isLoading)
        }

        detailViewModel.getFavUser().observe(this) { data ->
            val isFavUser = data.find { it.id == userId }
            isFavorite = isFavUser != null
            updateFavoriteButton()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        detailBinding.loading.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun updateFavoriteButton() {

        val drawableRes = if (isFavorite) {
            R.drawable.outline_favorite_24
        } else {
            R.drawable.outline_favorite_border_24
        }
        detailBinding.favButton.setImageDrawable(ContextCompat.getDrawable(detailBinding.favButton.context, drawableRes))
    }
}
