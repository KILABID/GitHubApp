package com.bangkit.submissionAwal.ui.favoritePage

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.bangkit.submissionAwal.data.local.entity.FavoritEntity
import com.bangkit.submissionAwal.data.local.repository.FavoriteRepository

class FavoriteViewModel(application: Application) : ViewModel() {
    private val favRepository = FavoriteRepository(application)
    
    fun getFavUser(): LiveData<List<FavoritEntity>> = favRepository.getFavUser()

}