package com.easycoding.todou.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.easycoding.todou.R
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

            val categoryDao = database.get().getCategoryDao()
            val todoDao = database.get().getTodoDao()
            applicationScope.launch {
                // create category
                categoryDao.insert(CategoryEntity(0, "Default", R.color.category_color_1, 0))
                categoryDao.insert(CategoryEntity(1, "Home", R.color.category_color_2, 1))
                categoryDao.insert(CategoryEntity(2, "Work", R.color.category_color_3, 2))

                // create todos `Default` category
                todoDao.insert(TodoEntity(0, "Meet with friend", isImportant = true, categoryId = 0))
                todoDao.insert(TodoEntity(0, "Buy sneekers", isImportant = false, categoryId = 0))
                todoDao.insert(TodoEntity(0, "Call Mom", isImportant = false, categoryId = 0))
                todoDao.insert(TodoEntity(0, "Invent time machine", isImportant = true, categoryId = 0))
                todoDao.insert(TodoEntity(0, "Read a book", isImportant = false, categoryId = 0))

                // create todos `Home` category
                todoDao.insert(TodoEntity(0, "Buy milk", isImportant = true, categoryId = 1))
                todoDao.insert(TodoEntity(0, "Buy bread", isImportant = false, categoryId = 1))
                todoDao.insert(TodoEntity(0, "Repair a stool", isImportant = false, categoryId = 1))
            }
        }
    }
}