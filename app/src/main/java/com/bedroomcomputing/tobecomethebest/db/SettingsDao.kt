package com.bedroomcomputing.tobecomethebest.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SettingsDao {
    @Query("SELECT * FROM settings where id = 0")
    fun getSettings(): LiveData<Settings>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSettings(vararg settings: Settings)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(settings: Settings)

    @Query("DELETE FROM settings WHERE id =:id")
    suspend fun delete(id:Int)
}