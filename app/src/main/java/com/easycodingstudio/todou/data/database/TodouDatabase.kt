package com.easycodingstudio.todou.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.easycodingstudio.todou.R
import com.easycodingstudio.todou.data.database.dao.CategoryDao
import com.easycodingstudio.todou.data.database.dao.CategoriesWithTodosDao
import com.easycodingstudio.todou.data.database.dao.TodoDao
import com.easycodingstudio.todou.data.database.entities.CategoryEntity
import com.easycodingstudio.todou.data.database.entities.CategoryWithTodosEntity
import com.easycodingstudio.todou.data.database.entities.TodoEntity
import com.easycodingstudio.todou.di.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

const val TODOU_DATABASE_NAME = "todou_database"

@Database(entities = [
    CategoryEntity::class,
    TodoEntity::class
], version = 1)
abstract class TodouDatabase: RoomDatabase() {
    abstract fun getCategoriesWithTodosDao(): CategoriesWithTodosDao
    abstract fun getTodoDao(): TodoDao
    abstract fun getCategoryDao(): CategoryDao

    class Callback @Inject constructor(
        // Provider helps to inject the database after hilt creates it.
        private val database: Provider<TodouDatabase>,
        @ApplicationScope private val applicationScope: CoroutineScope
    ): RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            val categoryDao = database.get().getCategoryDao()
            val todoDao = database.get().getTodoDao()
            applicationScope.launch {
                // create category
                categoryDao.insert(CategoryEntity(1, "Todo", R.color.category_color_1, 1))
                categoryDao.insert(CategoryEntity(2, "Home", R.color.category_color_2, 2))
                categoryDao.insert(CategoryEntity(3, "Work", R.color.category_color_3, 3))

                // create todos `Todos` category
                todoDao.insert(TodoEntity(task = "Learn english words", isImportant = false, categoryId = 1))
                todoDao.insert(TodoEntity(task = "Watch nine-nine brooklyn", isImportant = false, categoryId = 1))

                // create todos `Home` category
                todoDao.insert(TodoEntity(task = "Meet with friend", isImportant = true, categoryId = 2))
                todoDao.insert(TodoEntity(task = "Buy sneekers", isImportant = false, categoryId = 2))
                todoDao.insert(TodoEntity(task = "Call Mom", isImportant = false, categoryId = 2))
                todoDao.insert(TodoEntity(task = "Invent time machine", isImportant = true, categoryId = 2))
                todoDao.insert(TodoEntity(task = "Read a book", isImportant = false, categoryId = 2))

                // create todos `Work` category
                todoDao.insert(TodoEntity(task = "Continue Todou project", isImportant = true, categoryId = 3))
                todoDao.insert(TodoEntity(task = "Finish Cancel/Payment subscription popup", isImportant = false, categoryId = 3))
                todoDao.insert(TodoEntity(task = "Repair a stool", isImportant = false, categoryId = 3))
            }
        }
    }
}