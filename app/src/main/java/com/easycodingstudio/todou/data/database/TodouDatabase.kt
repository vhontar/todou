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
        private val database: Provider<TodouDatabase>,
        @ApplicationScope private val applicationScope: CoroutineScope
    ): RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            val categoryDao = database.get().getCategoryDao()
            val todoDao = database.get().getTodoDao()
            applicationScope.launch {
                // create category
                categoryDao.insert(CategoryEntity(0, "Todo", R.color.category_color_1, 0))
                categoryDao.insert(CategoryEntity(1, "Home", R.color.category_color_2, 1))
                categoryDao.insert(CategoryEntity(2, "Work", R.color.category_color_3, 2))

                // create todos `Todos` category
                todoDao.insert(TodoEntity(0, "Meet with friend", isImportant = true, categoryId = 0))
                todoDao.insert(TodoEntity(1, "Buy sneekers", isImportant = false, categoryId = 0))
                todoDao.insert(TodoEntity(2, "Call Mom", isImportant = false, categoryId = 0))
                todoDao.insert(TodoEntity(3, "Invent time machine", isImportant = true, categoryId = 0))
                todoDao.insert(TodoEntity(4, "Read a book", isImportant = false, categoryId = 0))

                // create todos `Home` category
                todoDao.insert(TodoEntity(5, "Buy milk", isImportant = true, categoryId = 1))
                todoDao.insert(TodoEntity(6, "Buy bread", isImportant = false, categoryId = 1))
                todoDao.insert(TodoEntity(7, "Repair a stool", isImportant = false, categoryId = 1))
            }
        }
    }
}