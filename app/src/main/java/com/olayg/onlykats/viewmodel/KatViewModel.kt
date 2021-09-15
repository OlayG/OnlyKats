package com.olayg.onlykats.viewmodel

import android.util.Log
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
import javax.security.auth.login.LoginException

// TODO: 9/10/21 Get the breedState from repo and load into the breedState livedata
// TODO: 9/10/21 Once you the breedState from repo make sure you update isNextPage
class KatViewModel : ViewModel() {

    private  val TAG = "KatViewModel";
    private val _katState = MutableLiveData<ApiState<List<Kat>>>()
    val katState: LiveData<ApiState<List<Kat>>>
        get() = _katState

    private val _breedState = MutableLiveData<ApiState<List<Breed>>>()
    val breedState: LiveData<ApiState<List<Breed>>> get() = _breedState

    // This lets us combine multiple livedata's into 1, I am using this to update settings anytime
    // the states change
    val stateUpdated = MediatorLiveData<Unit>().apply {
        addSource(_katState) { value = Unit }
        addSource(_breedState) { value = Unit }
    }

    var queries: Queries? = null

    private var isNextPage = false
    private var currentPage = -1

    fun fetchData(queries: Queries) {
        this.queries = queries


        Log.i(TAG, "the endpoint is ${queries.endPoint}: ")
        fetchData(PageAction.FIRST)
    }

    fun fetchData(pageAction: PageAction) {
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
            Log.i(TAG, "getBreeds called ")
            KatRepo.getBreedState(queries).collect {breedState ->
                Log.i(TAG, "got breed state with ${breedState}: ")
                isNextPage = breedState !is ApiState.EndOfPage
                _breedState.postValue(breedState)}
        }
    }

    private fun PageAction.update(page: Int) = when (this) {
        PageAction.FIRST -> 0
        PageAction.NEXT -> page.inc()
        PageAction.PREV -> if (page > 0) page.dec() else page
    }

}
