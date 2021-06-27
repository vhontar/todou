package com.easycoding.todou.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.easycoding.todou.data.database.dao.CategoryDao
import com.easycoding.todou.data.database.dao.CategoryWithTodosDao
import com.easycoding.todou.data.database.dao.TodoDao
import com.easycoding.todou.data.database.entities.CategoryEntity
import com.easycoding.todou.data.database.entities.CategoryWithTodosEntity
import com.easycoding.todou.data.database.entities.TodoEntity
import com.easycoding.todou.di.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

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

    class Callback @Inject constructor(
        private val database: Provider<TodouDatabase>,
        @ApplicationScope private val applicationScope: CoroutineScope
    ): RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            // TODO finish set up default values
            val categoryDao = database.get().getCategoryDao()
            val todoDao = database.get().getTodoDao()
            applicationScope.launch {
                // categoryDao.insert(CategoryEntity(0, "Default", ))
            }
        }
    }
}