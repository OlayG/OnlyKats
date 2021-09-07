package com.olayg.onlykats.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.olayg.onlykats.model.Kat
import com.olayg.onlykats.repo.KatRepo
import com.olayg.onlykats.util.ApiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class KatViewModel : ViewModel() {

    private val _katState = MutableLiveData<ApiState<List<Kat>>>()
    val katState: LiveData<ApiState<List<Kat>>>
        get() = _katState

    fun fetchKatList(limit: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            KatRepo.getKatState(limit).collect { katState -> _katState.postValue(katState) }
        }
    }
}