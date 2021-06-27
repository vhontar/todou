package com.easycoding.todou.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.easycoding.todou.data.entities.CategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao: AaaDao<CategoryEntity> {

    @Query("SELECT * FROM categories")
    fun getCategories(): Flow<List<CategoryEntity>>
}