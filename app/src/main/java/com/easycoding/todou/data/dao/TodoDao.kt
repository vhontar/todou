package com.easycoding.todou.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.easycoding.todou.data.entities.TodoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao: AaaDao<TodoEntity> {

    @Query("SELECT * FROM todos WHERE category_id = :categoryId")
    fun getTodosForCategory(categoryId: Int): Flow<List<TodoEntity>>
}