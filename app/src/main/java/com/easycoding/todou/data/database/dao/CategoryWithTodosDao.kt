package com.easycoding.todou.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.easycoding.todou.data.database.entities.CategoryWithTodosEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryWithTodosDao: AaaDao<CategoryWithTodosEntity> {

    @Transaction
    @Query("SELECT * FROM categories")
    fun getCategoriesWithTodos(): Flow<List<CategoryWithTodosEntity>>
}