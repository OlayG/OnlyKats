package com.olayg.onlykats.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.olayg.onlykats.model.request.Queries
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class PreferenceManager private constructor(val dataStore: DataStore<Preferences>) {

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

    companion object {
        private var INSTANCE: PreferenceManager? = null

        fun getInstance(context: Context): PreferenceManager {
            if (INSTANCE == null) INSTANCE = PreferenceManager(context.dataStore)
            return INSTANCE!!
        }
    }
}