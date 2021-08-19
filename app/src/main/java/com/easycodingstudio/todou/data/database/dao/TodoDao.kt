package com.easycodingstudio.todou.data.database.dao

import androidx.room.*
import com.easycodingstudio.todou.data.database.entities.CategoryWithTodosEntity
import com.easycodingstudio.todou.data.database.entities.TodoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {

    @Query("SELECT * FROM todos WHERE category_id = :categoryId")
    fun getTodosForCategory(categoryId: Long): Flow<List<TodoEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: TodoEntity): Long

    @Delete
    suspend fun delete(entity: TodoEntity)

    @Update
    suspend fun update(entity: TodoEntity)
}