package com.easycodingstudio.todou.repository

import com.easycodingstudio.todou.data.database.dao.CategoryDao
import com.easycodingstudio.todou.data.database.dao.TodoDao
import com.easycodingstudio.todou.data.database.entities.toModel
import com.easycodingstudio.todou.data.database.entities.toModels
import com.easycodingstudio.todou.model.Category
import com.easycodingstudio.todou.model.Todo
import com.easycodingstudio.todou.model.toEntities
import com.easycodingstudio.todou.model.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TodouRepository @Inject constructor(
    private val todoDao: TodoDao,
    private val categoryDao: CategoryDao
) {
    // CRUD category
    fun getCategories() = categoryDao.getCategories().map { it.toModels() }
    fun getCategory(categoryId: Long) = categoryDao.getCategory(categoryId).map { it.toModel() }
    fun getSelectedCategory() = categoryDao.getSelectedCategory().map { it?.toModel() }
    suspend fun createCategory(category: Category) = categoryDao.insert(category.toEntity())
    suspend fun updateCategory(category: Category) = categoryDao.update(category.toEntity())
    suspend fun updateAllCategories(categories: List<Category>) = categoryDao.updateAll(categories.toEntities())
    suspend fun deleteCategory(category: Category) = categoryDao.delete(category.toEntity())

    // CRUD todos
    fun getAllTodos(): Flow<List<Todo>> = todoDao.getAllTodos().map { it.toModels() }

    suspend fun createTodo(todo: Todo) = todoDao.insert(todo.toEntity())
    suspend fun updateTodo(todo: Todo) = todoDao.update(todo.toEntity())
    suspend fun deleteTodo(todo: Todo) = todoDao.delete(todo.toEntity())
}