package com.olayg.onlykats.repo.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.olayg.onlykats.model.room.KatData

@Dao
interface KatDataDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addKat(kat: KatData)

    @Update
    suspend fun updateKat(katData: KatData)

    @Delete
    suspend fun deleteKat(katData: KatData)

    @Query("DELETE FROM kat_table")
    suspend fun deleteAllKats()

    @Query("SELECT * FROM kat_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<KatData>>

}