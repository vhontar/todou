package com.easycodingstudio.todou.ui.todou

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.easycodingstudio.todou.model.Category
import com.easycodingstudio.todou.model.SortOrder
import com.easycodingstudio.todou.model.Todo
import com.easycodingstudio.todou.repository.TodouRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodouViewModel @Inject constructor(
    private val todouRepository: TodouRepository
) : ViewModel() {

    val categoriesWithTodos = todouRepository.getCategoriesWithTodos("", false, SortOrder.SORT_BY_DATE).asLiveData()

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

    fun onSortOrderSelected(sortOrder: SortOrder) = viewModelScope.launch {
        // save to data store
    }

    fun onHideCompletedClicked(isHidden: Boolean) = viewModelScope.launch {
        // save to data store
    }

    fun onDeleteAllCompletedClicked() = viewModelScope.launch {
        // show confirmation dialog
    }

    sealed class TodouEvents() {
        class NavigateToCategoryWithAllTodosPage(val category: Category) : TodouEvents()
        class NavigateToCategoryPage(val category: Category) : TodouEvents()
        class NavigateToTodoPage(val todo: Todo) : TodouEvents()
    }
}