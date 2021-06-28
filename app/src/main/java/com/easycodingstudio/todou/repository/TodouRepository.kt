package com.easycodingstudio.todou.repository

import com.easycodingstudio.todou.data.database.dao.CategoryDao
import com.easycodingstudio.todou.data.database.dao.CategoriesWithTodosDao
import com.easycodingstudio.todou.data.database.dao.TodoDao
import com.easycodingstudio.todou.data.database.entities.toModels
import com.easycodingstudio.todou.model.Category
import com.easycodingstudio.todou.model.SortOrder
import com.easycodingstudio.todou.model.Todo
import com.easycodingstudio.todou.model.toEntity
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TodouRepository @Inject constructor(
    private val todoDao: TodoDao,
    private val categoryDao: CategoryDao,
    private val categoryWithTodosDao: CategoriesWithTodosDao
) {
    // get category with todos
    fun getCategoriesWithTodos(searchQuery: String, hideCompleted: Boolean, sortOrder: SortOrder) =
        categoryWithTodosDao.getCategoriesWithTodos(searchQuery, hideCompleted, sortOrder).map { it.toModels() }

    // CRUD category
    suspend fun createCategory(category: Category) = categoryDao.insert(category.toEntity())
    fun getCategories() = categoryDao.getCategories()
    suspend fun updateCategory(category: Category) = categoryDao.update(category.toEntity())
    suspend fun deleteCategory(category: Category) = categoryDao.delete(category.toEntity())

    // CRUD todos
    suspend fun createTodo(todo: Todo, category: Category) =
        todoDao.insert(todo.copy(categoryId = category.id).toEntity())

    fun getTodosForCategory(category: Category) = todoDao.getTodosForCategory(category.id)
    suspend fun updateTodo(todo: Todo) = todoDao.update(todo.toEntity())
    suspend fun deleteTodo(todo: Todo) = todoDao.delete(todo.toEntity())
}