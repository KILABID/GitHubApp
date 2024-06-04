package com.bangkit.submissionAwal.data.local.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.bangkit.submissionAwal.data.local.entity.FavoritEntity
import com.bangkit.submissionAwal.data.local.room.FavoriteDao
import com.bangkit.submissionAwal.data.local.room.FavoriteDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteRepository(application: Application)  {
    private val favDao : FavoriteDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteDatabase.getDatabase(application)
        favDao = db.favDao()
    }

    fun addFav(fav: FavoritEntity) {
        executorService.execute { favDao.insert(fav) }
    }

    fun removeFav(fav: FavoritEntity) {
        executorService.execute { favDao.delete(fav) }
    }

    fun getFavUser(): LiveData<List<FavoritEntity>> = favDao.getFavUser()


}