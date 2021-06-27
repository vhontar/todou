package com.easycoding.todou.data.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update

interface AaaDao<Entity> {
    @Insert
    fun insert(entity: Entity): Long

    @Delete
    fun delete(entity: Entity): Long

    @Update
    fun update(entity: Entity): Long
}