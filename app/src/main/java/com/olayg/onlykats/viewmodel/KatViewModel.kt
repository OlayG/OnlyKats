package com.olayg.onlykats.viewmodel

import androidx.lifecycle.*
import com.olayg.onlykats.model.Breed
import com.olayg.onlykats.model.Kat
import com.olayg.onlykats.model.request.Queries
import com.olayg.onlykats.repo.KatRepo
import com.olayg.onlykats.util.ApiState
import com.olayg.onlykats.util.EndPoint
import com.olayg.onlykats.util.PageAction
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class KatViewModel: ViewModel() {

    private val _katState = MutableLiveData<ApiState<List<Kat>>>()
    val katState: LiveData<ApiState<List<Kat>>> get() = _katState

    private val _breedState = MutableLiveData<ApiState<List<Breed>>>()
    val breedState: LiveData<ApiState<List<Breed>>> get() = _breedState

    // This lets us combine multiple livedata's into 1, I am using this to update settings anytime the states change
    val stateUpdated : LiveData<Unit> = MediatorLiveData<Unit>().apply {
        addSource(_katState) { value = Unit }
        addSource(_breedState) { value = Unit }
    }

    var queries: Queries? = null
    private var isNextPage = false
    private var currentPage = -1
    var currentPageAction = PageAction.FIRST

    fun fetchData(queries: Queries) {
        this.queries = queries
        fetchData(PageAction.FIRST)
    }

    fun fetchData(pageAction: PageAction) {
        currentPageAction = pageAction
        if (_katState.value !is ApiState.Loading) queries?.let { query ->
            query.page = pageAction.update(query.page ?: -1)
            val shouldFetchPage = isNextPage || pageAction == PageAction.FIRST
            if (shouldFetchPage) {
                currentPage = query.page!!
                when (query.endPoint) {
                    EndPoint.IMAGES -> getImages(query)
                    EndPoint.BREEDS -> getBreeds(query)
                }
            }
        }
    }

    private fun getImages(queries: Queries) {
        viewModelScope.launch {
            KatRepo.getKatState(queries).collect { katState ->
                isNextPage = katState !is ApiState.EndOfPage
                _katState.postValue(katState)
            }
        }
    }

    private fun getBreeds(queries: Queries) {
        viewModelScope.launch {
            KatRepo.getBreedState(queries).collect { breedState ->
                isNextPage = breedState !is ApiState.EndOfPage
                _breedState.postValue(breedState)
            }
        }
    }

    private fun PageAction.update(page: Int) = when (this) {
        PageAction.FIRST -> 0
        PageAction.NEXT -> page.inc()
        PageAction.PREV -> if (page > 0) page.dec() else page
    }
}