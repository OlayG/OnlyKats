package com.olayg.onlykats.util

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

//class SettingsManager(private val dataStore : DataStore<Preferences>) {
//
//    suspend fun saveSettings(limit:Int, endpoint:String) {
//        dataStore.edit {
//            it[LIMIT] = limit
//            it[ENDPOINT] = endpoint
//        }
//    }
//
//    companion object {
//        val LIMIT = intPreferencesKey("limit")
//        val ENDPOINT = stringPreferencesKey("endpoint")
//    }
//}