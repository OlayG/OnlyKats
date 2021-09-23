package com.olayg.onlykats.repo.local.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.olayg.onlykats.model.request.Queries
import com.olayg.onlykats.util.EndPoint
import com.olayg.onlykats.util.PreferenceKey
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")


@Singleton
class UserPrefManager @Inject  constructor(@ApplicationContext val context: Context)
{
    private val dataStore by lazy {context.dataStore}
    val queries
        get() = dataStore.data.map { preferences ->
            preferences[PreferenceKey.ENDPOINT]?.let {
                Queries(
                    endPoint = EndPoint.valueOf(it),
                    limit = preferences[PreferenceKey.LIMIT] ?: 10,
                    page = 0
                )
            }
        }
    suspend fun saveQuery()
    {
    }
}