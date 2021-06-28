package com.easycodingstudio.todou.data.database.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

interface AaaDao<Entity> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: Entity): Long

    @Delete
    suspend fun delete(entity: Entity): Long

    @Update
    suspend fun update(entity: Entity): Long
}