package com.olayg.onlykats.util

import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object PreferencesKey {
    val ENDPOINT = stringPreferencesKey("endpoint")
    val LIMIT = floatPreferencesKey("limit")
}