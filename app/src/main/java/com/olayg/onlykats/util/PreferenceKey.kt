package com.olayg.onlykats.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore


object PreferenceKey {
    val ENDPOINT = stringPreferencesKey("name")
    val LIMIT = intPreferencesKey("slide")
}
//
//class PrefSelection(private  val context: Context) {
//    companion object QUERY {
//        val ENDPOINT = stringPreferencesKey("endpoint")
//        val LIMIT = intPreferencesKey("limit")
//        val PAGE = intPreferencesKey("page")
//    }
//
//    private val Context.queryDataStore: DataStore<Preferences> by preferencesDataStore("setting")
//
//    suspend fun putData(key: String, value: String) {
//         val dataStoreKey = stringPreferencesKey(key)
//        context.queryDataStore.edit { setting ->
//            setting[QUERY.ENDPOINT] = value
//            setting[QUERY.LIMIT] = value.toInt()
//            setting[QUERY.PAGE] = value.toInt()
//        }
//    }
//
//    suspend fun getData(key: String) {
//        val dataStoreKey = stringPreferencesKey(key)
//    }
//}


