 package com.olayg.onlykats.repo

import com.olayg.onlykats.model.Breed
import com.olayg.onlykats.model.Category
import com.olayg.onlykats.model.Kat
import com.olayg.onlykats.model.request.Queries
import com.olayg.onlykats.repo.local.dao.BreedDao
import com.olayg.onlykats.repo.local.dao.KatDao
import com.olayg.onlykats.repo.remote.KatService
import com.olayg.onlykats.util.ApiState
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject
@ViewModelScoped
 class KatRepo @Inject constructor(
    private val katService: KatService,
    private val katDao: KatDao,
    private val breedDao: BreedDao,
 ) {

    suspend fun getKatState(queries: Queries) : Flow<List<Kat>> {


        val katFlow = katDao.getAll()
        val katResponse = katService.getKatImages(queries.asQueryMap)
        if (!katResponse.body().isNullOrEmpty())
            katDao.insertAll(*katResponse.body()!!.toTypedArray())
        return katFlow
    }


    suspend fun getBreedState( queries: Queries) : Flow<List<Breed>> {

        val breedFlow =  breedDao.getAll()
        val breedResponse = katService.getBreeds(queries.asQueryMap)
        if (!breedResponse.body().isNullOrEmpty())
            breedDao.insertAll(*breedResponse.body()!!.toTypedArray())
        return breedFlow
    }


    private val Queries.asQueryMap: Map<String, Any>
        get() = listOfNotNull(
            "limit" to limit,
            page?.let { "page" to it }
        ).toMap()
}

private fun <T> Response<List<T>>.getApiState(): ApiState<List<T>> {
    return if (isSuccessful)
    {
        if (body().isNullOrEmpty()) { ApiState.EndOfPage }
        else { ApiState.Success(body()!!) }
    } else { ApiState.Failure("Error fetching data.") }
}