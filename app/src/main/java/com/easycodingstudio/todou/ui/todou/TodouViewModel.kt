package com.easycodingstudio.todou.ui.todou

import androidx.lifecycle.*
import com.easycodingstudio.todou.model.*
import com.easycodingstudio.todou.repository.TodouRepository
import com.easycodingstudio.todou.ui.adapter.OnCategoryMenuItemListener
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
) : ViewModel(), OnTodoItemListener, OnCategoryMenuItemListener {

    val todos = todouRepository.getAllTodos().asLiveData()
    val categories = todouRepository.getCategories().asLiveData()
    val selectedCategory = todouRepository.getSelectedCategory().asLiveData()

    // todos
    val todosWithHeader = MediatorLiveData<List<Holder>>()
    val homeScreenSelected = MediatorLiveData<Boolean>()

    private val todouEventsChannel = Channel<TodouEvents>()
    val todouEvents = todouEventsChannel.receiveAsFlow()

    init {
        todosWithHeader.addSource(todos) { generateTodosWithHeadersForHomePage(selectedCategory.value, it) }
        todosWithHeader.addSource(selectedCategory) { generateTodosWithHeadersForHomePage(it, todos.value ?: listOf()) }

        // calculate if home screen selected depends on categories
        homeScreenSelected.addSource(categories) { categories ->
            homeScreenSelected.value = categories.none { it.selected }
        }
    }

    override fun onHomeScreenItemClicked() {
        viewModelScope.launch {
            categories.value?.let {
                it.forEach { category -> category.selected = false }
                todouRepository.updateAllCategories(it)
            }
        }
    }

    override fun onCategoryMenuItemClicked(category: Category) {
        viewModelScope.launch {
            val oldCategories = categories.value ?: listOf()
            oldCategories.forEach {
                it.selected = it.id == category.id
            }
            todouRepository.updateAllCategories(oldCategories)
            todouEventsChannel.send(TodouEvents.CloseDrawerNavigation)
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

    override fun onTodoHeaderItemClicked(type: TodoHeaderType) {
        // create todos for current date
    }

    // TODO get from datastore
    fun isDarkModeEnabled() = true

    // TODO refactor filter
    private fun generateTodosWithHeadersForHomePage(
        category: Category?,
        todos: List<Todo>
    ) {
        val finalList = arrayListOf<Holder>()
        // today
        val today = DateTime.now()
        val todayTodos = if (category == null) {
            todos.filter { todo -> todo.todoDate?.dayOfMonth == today.dayOfMonth }
                .map { TodoWithCategory(getCategoryById(it.categoryId), it) }
        } else {
            todos.filter { todo -> todo.categoryId == category.id && todo.todoDate?.dayOfMonth == today.dayOfMonth }
                .map { TodoWithCategory(category, it) }
        }
        finalList.add(TodoHeader( TodoHeaderType.TODAY, todayTodos.size, 0))
        finalList.addAll(todayTodos)
        // tomorrow
        val tomorrow = DateTime.now().plusDays(1)
        val tomorrowTodos = if (category == null) {
            todos.filter { todo -> todo.todoDate?.dayOfMonth == tomorrow.dayOfMonth }
                .map { TodoWithCategory(getCategoryById(it.categoryId), it) }
        } else {
            todos.filter { todo -> todo.categoryId == category.id && todo.todoDate?.dayOfMonth == tomorrow.dayOfMonth }
                .map { TodoWithCategory(category, it) }
        }
        finalList.add(TodoHeader(TodoHeaderType.TOMORROW, tomorrowTodos.size, 0))
        finalList.addAll(tomorrowTodos)
        // later
        val laterTodos = if (category == null) {
            todos.filter { todo -> todo.todoDate == null || (todo.todoDate?.isAfterNow == true && todo.todoDate?.dayOfMonth != today.dayOfMonth && todo.todoDate?.dayOfMonth != tomorrow.dayOfMonth) }
                .map { TodoWithCategory(getCategoryById(it.categoryId), it) }
        } else {
            todos.filter { todo -> todo.categoryId == category.id && (todo.todoDate == null || (todo.todoDate?.isAfterNow == true && todo.todoDate?.dayOfMonth != today.dayOfMonth && todo.todoDate?.dayOfMonth != tomorrow.dayOfMonth)) }
                .map { TodoWithCategory(category, it) }
        }
        finalList.add(TodoHeader(TodoHeaderType.LATER, laterTodos.size, 0))
        finalList.addAll(laterTodos)

        // TODO filter by importance

        todosWithHeader.value = finalList
    }

    private fun getCategoryById(id: Long): Category {
        return categories.value?.filter { it.id == id }?.get(0) ?: Category()
    }

    sealed class TodouEvents {
        class NavigateToCategoryWithAllTodosPage(val category: Category) : TodouEvents()
        class NavigateToCategoryPage(val category: Category) : TodouEvents()
        class NavigateToTodoPage(val todo: Todo) : TodouEvents()
        object CloseDrawerNavigation : TodouEvents()
    }
}