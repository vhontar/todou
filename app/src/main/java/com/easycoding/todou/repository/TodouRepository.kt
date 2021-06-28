package com.easycoding.todou.repository

import com.easycoding.todou.data.database.dao.CategoryDao
import com.easycoding.todou.data.database.dao.CategoriesWithTodosDao
import com.easycoding.todou.data.database.dao.TodoDao
import com.easycoding.todou.data.database.entities.toModels
import com.easycoding.todou.model.Category
import com.easycoding.todou.model.Todo
import com.easycoding.todou.model.toEntity
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
    fun getCategoriesWithTodos() = categoryWithTodosDao.getCategoriesWithTodos().map { it.toModels() }

    // CRUD category
    fun createCategory(category: Category) = categoryDao.insert(category.toEntity())
    fun getCategories() = categoryDao.getCategories()
    fun updateCategory(category: Category) = categoryDao.update(category.toEntity())
    fun deleteCategory(category: Category) = categoryDao.delete(category.toEntity())

    // CRUD todos
    fun createTodo(todo: Todo, category: Category) =
        todoDao.insert(todo.copy(categoryId = category.id).toEntity())
    fun getTodosForCategory(category: Category) = todoDao.getTodosForCategory(category.id)
    fun updateTodo(todo: Todo) = todoDao.update(todo.toEntity())
    fun deleteTodo(todo: Todo) = todoDao.delete(todo.toEntity())
}