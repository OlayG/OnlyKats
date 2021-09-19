package com.olayg.onlykats.repo

import androidx.lifecycle.LiveData

import com.olayg.onlykats.model.room.KatData
import com.olayg.onlykats.repo.local.KatDataDao

class KatDataRepo(private val katDao: KatDataDao) {

    val readAllData: LiveData<List<KatData>> = katDao.readAllData()

    fun addKat(kat: KatData) {
        katDao.addKat(kat)
    }

    suspend fun updateKat(kat: KatData) {
        katDao.updateKat(kat)
    }

    suspend fun deleteKat(kat: KatData) {
        katDao.deleteKat(kat)
    }

    suspend fun deleteAllKats() {
        katDao.deleteAllKats()
    }
}