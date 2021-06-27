package com.easycoding.todou.ui.todou

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.easycoding.todou.repository.TodouRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TodouViewModel @Inject constructor(
    private val todouRepository: TodouRepository
): ViewModel() {

    val categoriesWithTodos = todouRepository.getCategoriesWithTodos().asLiveData()
}