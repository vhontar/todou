package com.easycoding.todou.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.easycoding.todou.data.dao.CategoryDao
import com.easycoding.todou.data.dao.CategoryWithTodosDao
import com.easycoding.todou.data.dao.TodoDao
import com.easycoding.todou.data.entities.CategoryEntity
import com.easycoding.todou.data.entities.CategoryWithTodosEntity
import com.easycoding.todou.data.entities.TodoEntity

const val TODOU_DATABASE_NAME = "todou_database"

@Database(entities = [
    CategoryEntity::class,
    TodoEntity::class,
    CategoryWithTodosEntity::class
], version = 1)
abstract class TodouDatabase: RoomDatabase() {
    abstract fun getCategoryWithTodosDao(): CategoryWithTodosDao
    abstract fun getTodoDao(): TodoDao
    abstract fun getCategoryDao(): CategoryDao
}