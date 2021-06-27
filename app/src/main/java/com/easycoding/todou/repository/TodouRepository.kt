package com.easycoding.todou.repository

import com.easycoding.todou.data.dao.CategoryDao
import com.easycoding.todou.data.dao.CategoryWithTodosDao
import com.easycoding.todou.data.dao.TodoDao
import com.easycoding.todou.model.Category
import com.easycoding.todou.model.Todo
import com.easycoding.todou.model.toEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TodouRepository @Inject constructor(
    private val todoDao: TodoDao,
    private val categoryDao: CategoryDao,
    private val categoryWithTodosDao: CategoryWithTodosDao
) {
    // get category with todos
    fun getCategoryWithTodos() = categoryWithTodosDao.getCategoriesWithTodos()

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