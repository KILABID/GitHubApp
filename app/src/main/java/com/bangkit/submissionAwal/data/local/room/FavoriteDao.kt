package com.bangkit.submissionAwal.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bangkit.submissionAwal.data.local.entity.FavoritEntity

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favorite : FavoritEntity)

    @Delete
    fun delete(favorite: FavoritEntity)

    @Query("SELECT * FROM favorite ORDER BY username ASC")
    fun getFavUser(): LiveData<List<FavoritEntity>>
}