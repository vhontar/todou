package com.easycodingstudio.todou.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataStoreManager @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private val Context.settingsDataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    private val dataStore = context.settingsDataStore

    val settingsDarkMode: Flow<Boolean> = dataStore.data.map {
        it[PreferenceKeys.settingsDarkMode] ?: false
    }
    val settingsFilterTodosByDate = dataStore.data.map {
        it[PreferenceKeys.settingsFilterTodosByDate] ?: true
    }
    val settingsFilterTodosByName = dataStore.data.map {
        it[PreferenceKeys.settingsFilterTodosByName] ?: false
    }
    val settingsHideCompletedTodos = dataStore.data.map {
        it[PreferenceKeys.settingsHideCompletedTodos] ?: false
    }

    suspend fun updateDarkModeSettings(darkMode: Boolean) {
        dataStore.edit {
            it[PreferenceKeys.settingsDarkMode] = darkMode
        }
    }

    suspend fun updateFilterTodosByDateSettings(filterTodosByDate: Boolean) {
        dataStore.edit {
            it[PreferenceKeys.settingsFilterTodosByDate] = filterTodosByDate
        }
    }

    suspend fun updateFilterTodosByNameSettings(filterTodosByName: Boolean) {
        dataStore.edit {
            it[PreferenceKeys.settingsFilterTodosByName] = filterTodosByName
        }
    }

    suspend fun updateHideCompletedTodosSettings(hideCompletedTodos: Boolean) {
        dataStore.edit {
            it[PreferenceKeys.settingsHideCompletedTodos] = hideCompletedTodos
        }
    }

    object PreferenceKeys {
        val settingsDarkMode: Preferences.Key<Boolean> = booleanPreferencesKey("settingsDarkMode")
        val settingsFilterTodosByName: Preferences.Key<Boolean> = booleanPreferencesKey("settingsFilterTodosByName")
        val settingsFilterTodosByDate: Preferences.Key<Boolean> = booleanPreferencesKey("settingsFilterTodosByDate")
        val settingsHideCompletedTodos: Preferences.Key<Boolean> = booleanPreferencesKey("settingsHideCompletedTodos")
    }
}