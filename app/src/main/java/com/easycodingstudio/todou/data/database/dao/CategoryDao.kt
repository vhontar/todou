package com.easycodingstudio.todou.data.database.dao

import androidx.room.*
import com.easycodingstudio.todou.data.database.entities.CategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {

    @Query("SELECT * FROM categories")
    fun getCategories(): Flow<List<CategoryEntity>>

    @Query("SELECT * FROM categories WHERE id = :categoryId")
    fun getCategory(categoryId: Long): Flow<CategoryEntity>

    @Query("SELECT * FROM categories WHERE selected = 1")
    fun getSelectedCategory(): Flow<CategoryEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: CategoryEntity): Long

    @Delete
    suspend fun delete(entity: CategoryEntity)

    @Update
    suspend fun update(entity: CategoryEntity)

    @Update
    suspend fun updateAll(entities: List<CategoryEntity>)
}