package com.bangkit.submissionAwal.ui.homePage

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.submissionAwal.R
import com.bangkit.submissionAwal.databinding.ActivityMainBinding
import com.bangkit.submissionAwal.ui.adapter.ListMainAdapter
import com.bangkit.submissionAwal.ui.detailPage.DetailActivity
import com.bangkit.submissionAwal.ui.favoritePage.FavoriteActivity
import com.bangkit.submissionAwal.ui.settings.SettingsActivity
import com.bangkit.submissionAwal.ui.settings.SettingsPreferences
import com.bangkit.submissionAwal.ui.settings.ViewModelFactory
import com.bangkit.submissionAwal.ui.settings.dataStore

class MainActivity : AppCompatActivity() {
    // Properties
    private lateinit var binding: ActivityMainBinding
    private lateinit var userInput: String
    private lateinit var mainViewModel: MainViewModel
    private lateinit var listAdapter: ListMainAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setupRecyclerView()
        setupObservers()
        setupSearchView()
        setupMenu()
    }

    private fun setupRecyclerView() {
        // Set Layout Recycler View
        val layoutManager = LinearLayoutManager(this)
        binding.rvUser.layoutManager = layoutManager
        listAdapter = ListMainAdapter()
        binding.rvUser.adapter = listAdapter

        // Set list click listener
        listAdapter.setOnItemClickListener { item ->
            val username = item.login.toString()
            val userId = item.id
            val intent = Intent(this@MainActivity, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_USER, username)
            intent.putExtra("EXTRA_ID", userId)
            startActivity(intent)
        }
    }

    private fun setupObservers() {
        // Inisialisasi ViewModel
        val pref = SettingsPreferences.getInstance(application.dataStore)
        mainViewModel = ViewModelProvider(this, ViewModelFactory(pref))[MainViewModel::class.java]

        // Observing data dari ViewModel
        mainViewModel.listUsers.observe(this) { users ->
            listAdapter.submitList(users)
        }

        mainViewModel.isLoading.observe(this) { isLoading ->
            showLoading(isLoading)
        }

        mainViewModel.toastError.observe(this) { error ->
            Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT).show()
        }

        val randomChar = generateRandomLetter().toString()
        mainViewModel.setSearch(randomChar)

    }

    private fun setupSearchView() {
        // Setup Search View
        val searchView = binding.searchView

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { _, _, _ ->
                    searchView.hide()
                    userInput = searchView.text.toString()
                    mainViewModel.setSearch(userInput)
                    false
                }
        }
    }

    private fun setupMenu() {

        // Setup Menu
        binding.searchBar.setOnMenuItemClickListener { menuItem ->
            when(menuItem.itemId){
                R.id.menuFavorite -> {
                    val favIntent = Intent(this@MainActivity, FavoriteActivity::class.java)
                    startActivity(favIntent)
                    true
                }
                R.id.themeMenu -> {
                    val setIntent = Intent(this@MainActivity, SettingsActivity::class.java)
                    startActivity(setIntent)
                    true
                }
                else -> false
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        // Show and Hide progress bar
        binding.progressbar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun generateRandomLetter(): Char {
        // Generate random char
        val alphabet = ('a'..'z')
        return alphabet.random()
    }
}
