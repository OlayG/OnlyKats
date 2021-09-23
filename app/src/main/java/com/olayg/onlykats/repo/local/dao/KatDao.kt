package com.olayg.onlykats.repo.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.olayg.onlykats.model.Kat
import kotlinx.coroutines.flow.Flow

@Dao
interface KatDao
{
    @Query("SELECT * FROM Kat")
    fun getAll(): Flow<List<Kat>>

    @Insert
    suspend fun insertAll(vararg kat: Kat)

    @Update
    suspend fun updateAll(kat: Kat)
}
