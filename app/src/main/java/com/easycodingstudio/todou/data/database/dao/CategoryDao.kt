package com.easycodingstudio.todou.data.database.dao

import androidx.room.*
import com.easycodingstudio.todou.data.database.entities.CategoryEntity
import com.easycodingstudio.todou.data.database.entities.CategoryWithTodosEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {

    @Query("SELECT * FROM categories")
    fun getCategories(): Flow<List<CategoryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: CategoryEntity): Long

    @Delete
    suspend fun delete(entity: CategoryEntity)

    @Update
    suspend fun update(entity: CategoryEntity)
}