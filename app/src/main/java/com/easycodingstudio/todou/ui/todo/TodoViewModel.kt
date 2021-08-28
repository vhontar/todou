package com.easycodingstudio.todou.ui.todo

import androidx.lifecycle.*
import com.easycodingstudio.todou.constants.ArgumentConstants
import com.easycodingstudio.todou.model.Category
import com.easycodingstudio.todou.model.Todo
import com.easycodingstudio.todou.model.TodoImportance
import com.easycodingstudio.todou.repository.TodouRepository
import com.easycodingstudio.todou.ui.adapter.OnCategorySelectionListener
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import org.joda.time.DateTime
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(
    private val todouRepository: TodouRepository,
    savedStateHandle: SavedStateHandle
): ViewModel(), OnCategorySelectionListener {

    // saved
    private val creationTodoDate = DateTime(savedStateHandle.get<Long>(ArgumentConstants.ARG_TODO_DATE_CREATION) ?: DateTime.now().millis)

    val categories = todouRepository.getCategories().asLiveData()
    val category = MediatorLiveData<Category>()

    val newTodo = MutableLiveData(Todo(todoDate = creationTodoDate))

    private val todoEventsChannel = Channel<TodoEvents>()
    val todoEvents = todoEventsChannel.receiveAsFlow()

    init {
        category.addSource(categories) { categories ->
            val selectedCategories = categories.filter { it.selected }
            category.value = if (selectedCategories.isEmpty()) categories[0] else selectedCategories[0]
        }
    }

    fun onCategorySelectionClicked() = viewModelScope.launch {
        todoEventsChannel.send(TodoEvents.OpenCategorySelectionDialog)
    }

    fun onCalendarMenuItemClicked() = viewModelScope.launch {
        todoEventsChannel.send(TodoEvents.OpenDateSelectionDialog)
    }

    fun onBellMenuItemClicked() = viewModelScope.launch {
        todoEventsChannel.send(TodoEvents.OpenLocalNotificationSetupDialog)
    }

    override fun onCategorySelectionItemClicked(category: Category) {
        this.category.value = category
        viewModelScope.launch {
            todoEventsChannel.send(TodoEvents.CloseCategorySelectionDialog)
        }
    }

    fun onImportanceMenuItemClicked() {
        val oldTodo = newTodo.value
        oldTodo?.let {
            if (it.importance == TodoImportance.DEFAULT) {
                it.importance = TodoImportance.HIGH_IMPORTANCE
            } else {
                it.importance = TodoImportance.DEFAULT
            }
        }
        newTodo.value = oldTodo
    }

    fun onDoneMenuItemClicked() = viewModelScope.launch {
        val localTodo = newTodo.value ?: return@launch

        todouRepository.createTodo(localTodo)
    }

    sealed class TodoEvents {
        object OpenDateSelectionDialog: TodoEvents()
        object OpenCategorySelectionDialog: TodoEvents()
        object OpenLocalNotificationSetupDialog: TodoEvents()
        object CloseTodoPage: TodoEvents()
        object CloseCategorySelectionDialog: TodoEvents()
    }
}