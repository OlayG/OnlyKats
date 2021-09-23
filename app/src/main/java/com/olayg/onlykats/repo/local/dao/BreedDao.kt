package com.olayg.onlykats.repo.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.olayg.onlykats.model.Breed
import kotlinx.coroutines.flow.Flow

@Dao
interface BreedDao
{
    @Query("SELECT * FROM Kat")
    fun getAll(): Flow<List<Breed>>

    @Insert
    suspend fun insertAll(vararg breed: Breed)

    @Update
    suspend fun updateAll(breed: Breed)
}