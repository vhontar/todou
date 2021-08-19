package com.easycodingstudio.todou.data.database.dao

import androidx.room.*
import com.easycodingstudio.todou.data.database.entities.CategoryWithTodosEntity
import com.easycodingstudio.todou.model.SortOrder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map

@Dao
interface CategoriesWithTodosDao {

    fun getCategoriesWithTodos(
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