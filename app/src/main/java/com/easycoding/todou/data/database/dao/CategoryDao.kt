package com.easycoding.todou.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.easycoding.todou.data.database.entities.CategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao: AaaDao<CategoryEntity> {

    @Query("SELECT * FROM categories")
    fun getCategories(): Flow<List<CategoryEntity>>
}