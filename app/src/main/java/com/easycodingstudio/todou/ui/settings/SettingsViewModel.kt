package com.easycodingstudio.todou.ui.settings

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.easycodingstudio.todou.data.datastore.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager
): ViewModel() {

    val settingsDarkMode = dataStoreManager.settingsDarkMode.asLiveData()
    val settingsFilterTodosByDate = dataStoreManager.settingsFilterTodosByDate.asLiveData()
    val settingsFilterTodosByName = dataStoreManager.settingsFilterTodosByName.asLiveData()
    val settingsHideCompletedTodos = dataStoreManager.settingsHideCompletedTodos.asLiveData()

    fun updateDarkModeSettings(darkMode: Boolean) = viewModelScope.launch {
        dataStoreManager.updateDarkModeSettings(darkMode)
    }

    fun updateFilterTodosByDateSettings(filterTodosByDate: Boolean) = viewModelScope.launch {
        dataStoreManager.updateFilterTodosByDateSettings(filterTodosByDate)
    }

    fun updateFilterTodosByNameSettings(filterTodosByName: Boolean) = viewModelScope.launch {
        dataStoreManager.updateFilterTodosByNameSettings(filterTodosByName)
    }

    fun updateHideCompletedTodosSettings(hideCompletedTodos: Boolean) = viewModelScope.launch {
        dataStoreManager.updateHideCompletedTodosSettings(hideCompletedTodos)
    }
}