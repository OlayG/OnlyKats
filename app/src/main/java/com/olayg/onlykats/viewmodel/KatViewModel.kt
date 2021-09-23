package com.olayg.onlykats.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.*
import com.olayg.onlykats.model.Breed
import com.olayg.onlykats.model.Category
import com.olayg.onlykats.model.Kat
import com.olayg.onlykats.model.request.Queries
import com.olayg.onlykats.repo.KatRepo
import com.olayg.onlykats.util.ApiState
import com.olayg.onlykats.util.EndPoint
import com.olayg.onlykats.util.PageAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
@HiltViewModel
class KatViewModel @Inject constructor(
    private val repo: KatRepo
) : ViewModel() {

    private val _katState = MutableLiveData<ApiState<List<Kat>>>()
    val katState: LiveData<ApiState<List<Kat>>>
        get() = _katState

    private val _breedState = MutableLiveData<ApiState<List<Breed>>>()
    val breedState: LiveData<ApiState<List<Breed>>>
        get() = _breedState


    // This lets us combine multiple livedata's into 1, I am using this to update settings anytime
    // the states change
    val stateUpdated = MediatorLiveData<Unit>().apply {
        addSource(_katState) { value = Unit }
        addSource(_breedState) { value = Unit }
    }

    var queries: Queries? = null

    private var isNextPage = false
    private var currentPage = -1
    var currentPagAction = PageAction.FIRST

    fun fetchData(queries: Queries) {
        this.queries = queries
        fetchData(PageAction.FIRST)
    }

    fun fetchData(pageAction: PageAction) {
        if (_katState.value !is ApiState.Loading) queries?.let { query ->
            currentPagAction = pageAction
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
        viewModelScope.launch(Dispatchers.IO) {
            repo.getKatState(queries).collect { katList ->
                val state = if (katList.isNullOrEmpty())ApiState.Failure("Did not load")
                else ApiState.Success(katList)
//                isNextPage = katState !is ApiState.EndOfPage
                _katState.postValue(state)
            }
        }
    }

    private fun getBreeds(queries: Queries) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getBreedState(queries).collect { breedList ->
                val state = if (breedList.isNullOrEmpty())ApiState.Failure("Did not load")
                else ApiState.Success(breedList)
                _breedState.postValue(state)
            }
        }
    }


    private fun PageAction.update(page: Int) = when (this) {
        PageAction.FIRST -> 0
        PageAction.NEXT -> page.inc()
        PageAction.PREV -> if (page > 0) page.dec() else page
    }
}
