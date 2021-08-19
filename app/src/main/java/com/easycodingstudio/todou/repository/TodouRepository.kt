package com.easycodingstudio.todou.repository

import com.easycodingstudio.todou.data.database.dao.CategoryDao
import com.easycodingstudio.todou.data.database.dao.CategoriesWithTodosDao
import com.easycodingstudio.todou.data.database.dao.TodoDao
import com.easycodingstudio.todou.data.database.entities.toModel
import com.easycodingstudio.todou.data.database.entities.toModels
import com.easycodingstudio.todou.model.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TodouRepository @Inject constructor(
    private val todoDao: TodoDao,
    private val categoryDao: CategoryDao,
    private val categoryWithTodosDao: CategoriesWithTodosDao
) {
    // get category with todos
    fun getCategoriesWithTodos(
        searchQuery: String,
        hideCompleted: Boolean,
        sortOrder: SortOrder
    ): Flow<List<CategoryWithTodos>> {
        return categoryWithTodosDao.getCategoriesWithTodos(sortOrder).map {
            it.filter { category ->
                category.todosEntities.filter { todo ->
                    if (searchQuery.isNotEmpty() && hideCompleted) {
                        todo.task.lowercase(Locale.US).contains(searchQuery.lowercase(Locale.US)) && !todo.isCompleted
                    } else if (hideCompleted) {
                        !todo.isCompleted
                    } else true
                }; true
            }.toModels()
        }
    }

    // CRUD category
    suspend fun createCategory(category: Category) = categoryDao.insert(category.toEntity())
    fun getCategories() = categoryDao.getCategories()
    fun getCategory(categoryId: Long) = categoryDao.getCategory(categoryId).map { it.toModel() }
    suspend fun updateCategory(category: Category) = categoryDao.update(category.toEntity())
    suspend fun deleteCategory(category: Category) = categoryDao.delete(category.toEntity())

    // CRUD todos
    suspend fun createTodo(todo: Todo, category: Category) =
        todoDao.insert(todo.copy(categoryId = category.id).toEntity())

    fun getTodosForCategory(category: Category) = todoDao.getTodosForCategory(category.id)
    suspend fun updateTodo(todo: Todo) = todoDao.update(todo.toEntity())
    suspend fun deleteTodo(todo: Todo) = todoDao.delete(todo.toEntity())
}