package com.easycoding.todou.data.database.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

interface AaaDao<Entity> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: Entity): Long

    @Delete
    fun delete(entity: Entity): Long

    @Update
    fun update(entity: Entity): Long
}