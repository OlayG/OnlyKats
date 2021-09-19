package com.olayg.onlykats.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

import com.olayg.onlykats.model.room.KatData
import com.olayg.onlykats.model.room.KatDatabase
import com.olayg.onlykats.repo.KatDataRepo

class KatDataViewModel(application: Application) : AndroidViewModel(application) {

    val katDataState: LiveData<List<KatData>>
    private val katRepo: KatDataRepo

    init {
        val katDao = KatDatabase.getInstance(application).katDao()
        katRepo = KatDataRepo(katDao)
        katDataState = katRepo.readAllData
    }

    fun addKatData(kat: KatData) {
        viewModelScope.launch(Dispatchers.IO) {
            katRepo.addKat(kat)
        }
    }

    fun updateKatData(kat: KatData) {
        viewModelScope.launch(Dispatchers.IO) {
            katRepo.updateKat(kat)
        }
    }

    fun deleteKatData(kat: KatData) {
        viewModelScope.launch(Dispatchers.IO) {
            katRepo.deleteKat(kat)
        }
    }

    fun deleteAllKats() {
        viewModelScope.launch(Dispatchers.IO) {
            katRepo.deleteAllKats()
        }
    }
}