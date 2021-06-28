package com.easycoding.todou.ui.todou

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.easycoding.todou.model.Category
import com.easycoding.todou.model.Todo
import com.easycoding.todou.repository.TodouRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodouViewModel @Inject constructor(
    private val todouRepository: TodouRepository
): ViewModel() {

    val categoriesWithTodos = todouRepository.getCategoriesWithTodos().asLiveData()

    private val todouEventsChannel = Channel<TodouEvents>()
    val todouEvents = todouEventsChannel.receiveAsFlow()

    fun onCategoryWithAllTodosClicked(category: Category) = viewModelScope.launch {
        todouEventsChannel.send(TodouEvents.NavigateToCategoryWithAllTodosPage(category))
    }
    fun onCategoryItemClicked(category: Category) = viewModelScope.launch {
        todouEventsChannel.send(TodouEvents.NavigateToCategoryPage(category))
    }
    fun onTodoItemClicked(todo: Todo) = viewModelScope.launch {
        todouEventsChannel.send(TodouEvents.NavigateToTodoPage(todo))
    }
    fun onTodoItemDoneClicked(todo: Todo) = viewModelScope.launch {
        todouRepository.updateTodo(todo)
    }

    sealed class TodouEvents() {
        class NavigateToCategoryWithAllTodosPage(val category: Category): TodouEvents()
        class NavigateToCategoryPage(val category: Category): TodouEvents()
        class NavigateToTodoPage(val todo: Todo): TodouEvents()
    }
}