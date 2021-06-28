package com.easycodingstudio.todou.ui.todos

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.easycodingstudio.todou.repository.TodouRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TodosViewModel @Inject constructor(
    private val todouRepository: TodouRepository,
    stateHandle: SavedStateHandle
) : ViewModel() {


}