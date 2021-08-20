package com.easycodingstudio.todou.ui.todos

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.easycodingstudio.todou.constants.ArgumentConstants
import com.easycodingstudio.todou.model.SortOrder
import com.easycodingstudio.todou.model.Todo
import com.easycodingstudio.todou.repository.TodouRepository
import com.easycodingstudio.todou.ui.adapter.OnTodoItemListener
import com.easycodingstudio.todou.ui.todou.TodouViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodosViewModel @Inject constructor(
    private val todouRepository: TodouRepository,
    stateHandle: SavedStateHandle
) : ViewModel(), OnTodoItemListener {

    val categoryId = stateHandle.get<Long>(ArgumentConstants.ARG_CATEGORY_ID) ?: 0
    val categoryWithTodos = todouRepository.getCategoryWithTodos(categoryId,"", true, SortOrder.SORT_BY_DATE).asLiveData()

    private val todosEventsChannel = Channel<TodouViewModel.TodouEvents>()
    val todosEvents = todosEventsChannel.receiveAsFlow()

    override fun onTodoItemClicked(todo: Todo) {
        viewModelScope.launch {
            todosEventsChannel.send(TodouViewModel.TodouEvents.NavigateToTodoPage(todo))
        }
    }

    override fun onTodoItemDoneClicked(todo: Todo) {
        viewModelScope.launch {
            todouRepository.updateTodo(todo)
        }
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

    fun onArchiveClicked() = viewModelScope.launch {
        todosEventsChannel.send(TodouViewModel.TodouEvents.NavigateToArchivePage)
    }
}