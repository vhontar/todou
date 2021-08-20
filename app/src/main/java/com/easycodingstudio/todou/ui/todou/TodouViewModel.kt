package com.easycodingstudio.todou.ui.todou

import androidx.lifecycle.*
import com.easycodingstudio.todou.model.Category
import com.easycodingstudio.todou.model.SortOrder
import com.easycodingstudio.todou.model.Todo
import com.easycodingstudio.todou.repository.TodouRepository
import com.easycodingstudio.todou.ui.adapter.OnCategoryTodoItemListener
import com.easycodingstudio.todou.ui.adapter.OnTodoItemListener
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import org.joda.time.DateTime
import javax.inject.Inject

@HiltViewModel
class TodouViewModel @Inject constructor(
    private val todouRepository: TodouRepository
) : ViewModel(), OnTodoItemListener, OnCategoryTodoItemListener {

    val categoriesWithTodos = todouRepository.getCategoriesWithTodos("", true, SortOrder.SORT_BY_DATE).asLiveData()

    private val todouEventsChannel = Channel<TodouEvents>()
    val todouEvents = todouEventsChannel.receiveAsFlow()

    private val currentDate = DateTime.now().toLocalDateTime()

    override fun onCategoryWithAllTodosClicked(category: Category) {
        viewModelScope.launch {
            todouEventsChannel.send(TodouEvents.NavigateToCategoryWithAllTodosPage(category))
        }
    }

    override fun onCategoryItemClicked(category: Category){
        viewModelScope.launch {
            todouEventsChannel.send(TodouEvents.NavigateToCategoryPage(category))
        }
    }

    override fun onTodoItemClicked(todo: Todo) {
        viewModelScope.launch {
            todouEventsChannel.send(TodouEvents.NavigateToTodoPage(todo))
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

    fun getDay() = currentDate.dayOfMonth.toString()
    fun getMonth() = currentDate.toString("MMM")
    fun getYear() = currentDate.year.toString()
    // TODO change always + 0 minutes should be 00 and so on
    fun getTime(): String = "${currentDate.hourOfDay}:${currentDate.minuteOfHour}"

    sealed class TodouEvents {
        class NavigateToCategoryWithAllTodosPage(val category: Category) : TodouEvents()
        class NavigateToCategoryPage(val category: Category) : TodouEvents()
        class NavigateToTodoPage(val todo: Todo) : TodouEvents()
    }
}