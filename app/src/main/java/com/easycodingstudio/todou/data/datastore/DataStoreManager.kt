package com.easycodingstudio.todou.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataStoreManager @Inject constructor(
    @ApplicationContext context: Context
) {

    private val Context.filtersDataStore: DataStore<Preferences> by preferencesDataStore(name = "filters")
    private val Context.settingsDataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    object PreferenceKeys {

    }
}