package com.easycodingstudio.todou.ui.todo

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.easycodingstudio.todou.data.database.dao.TodoDao
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(
    private val todoDao: TodoDao,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {
}