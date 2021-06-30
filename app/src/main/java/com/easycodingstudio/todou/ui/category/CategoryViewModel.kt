package com.easycodingstudio.todou.ui.category

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.easycodingstudio.todou.repository.TodouRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val todouRepository: TodouRepository,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {
}