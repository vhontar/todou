package com.easycodingstudio.todou.data.database.dao

import androidx.room.*
import com.easycodingstudio.todou.data.database.entities.CategoryWithTodosEntity
import com.easycodingstudio.todou.model.SortOrder
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoriesWithTodosDao {

    fun getCategoriesWithTodos(
        searchQuery: String,
        hideCompleted: Boolean,
        sortOrder: SortOrder
    ): Flow<List<CategoryWithTodosEntity>> {
        return when (sortOrder) {
            SortOrder.SORT_BY_NAME -> getCategoriesWithTodosSortedByName()
            SortOrder.SORT_BY_DATE -> getCategoriesWithTodosSortedByDate()
        }
    }

    @Transaction
    @Query("SELECT * FROM categories")
    fun getCategoriesWithTodosSortedByName(): Flow<List<CategoryWithTodosEntity>>

    @Transaction
    @Query("SELECT * FROM categories")
    fun getCategoriesWithTodosSortedByDate(): Flow<List<CategoryWithTodosEntity>>
}