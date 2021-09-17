package com.olayg.onlykats.util

import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object PreferenceKey {
    val ENDPOINT = stringPreferencesKey("endpoint")
    val LIMIT = intPreferencesKey("limit")
}
