package com.easycodingstudio.todou.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.easycodingstudio.todou.data.database.entities.CategoryWithTodosEntity
import com.easycodingstudio.todou.model.SortOrder
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoriesWithTodosDao : AaaDao<CategoryWithTodosEntity> {

    fun getCategoriesWithTodos(
        searchQuery: String,
        hideCompleted: Boolean,
        sortOrder: SortOrder
    ): Flow<List<CategoryWithTodosEntity>> {
        return when (sortOrder) {
            SortOrder.SORT_BY_NAME -> getCategoriesWithTodosSortedByName(searchQuery, hideCompleted)
            SortOrder.SORT_BY_DATE -> getCategoriesWithTodosSortedByDate(searchQuery, hideCompleted)
        }
    }

    @Transaction
    @Query("SELECT * FROM categories INNER JOIN todos WHERE name = :searchQuery")
    fun getCategoriesWithTodosSortedByName(
        searchQuery: String,
        hideCompleted: Boolean
    ): Flow<List<CategoryWithTodosEntity>>

    @Transaction
    @Query("SELECT * FROM categories INNER JOIN todos WHERE name = :searchQuery")
    fun getCategoriesWithTodosSortedByDate(
        searchQuery: String,
        hideCompleted: Boolean
    ): Flow<List<CategoryWithTodosEntity>>
}