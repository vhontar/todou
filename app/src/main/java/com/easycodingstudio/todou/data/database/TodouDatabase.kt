package com.easycodingstudio.todou.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.easycodingstudio.todou.R
import com.easycodingstudio.todou.data.database.dao.CategoryDao
import com.easycodingstudio.todou.data.database.dao.TodoDao
import com.easycodingstudio.todou.data.database.entities.CategoryEntity
import com.easycodingstudio.todou.data.database.entities.TodoEntity
import com.easycodingstudio.todou.di.ApplicationScope
import com.easycodingstudio.todou.model.TodoImportance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.joda.time.DateTime
import javax.inject.Inject
import javax.inject.Provider

const val TODOU_DATABASE_NAME = "todou_database"

@Database(entities = [
    CategoryEntity::class,
    TodoEntity::class
], version = 1)
abstract class TodouDatabase: RoomDatabase() {
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
                categoryDao.insert(CategoryEntity(1, "Home", R.color.category_color_1, selected = true, order = 1))
                categoryDao.insert(CategoryEntity(2, "Work", R.color.category_color_2, selected = false, order = 2))
                categoryDao.insert(CategoryEntity(3, "Shopping", R.color.category_color_3, selected = false, order = 3))
                categoryDao.insert(CategoryEntity(4, "Routine", R.color.category_color_4, selected = false, order = 4))
                categoryDao.insert(CategoryEntity(5, "Sport", R.color.category_color_5, selected = false, order = 5))

                // create todos `Home` category
                todoDao.insert(TodoEntity(task = "Learn english words", importance = TodoImportance.HIGH_IMPORTANCE, todoDate = DateTime.now().millis, categoryId = 1))
                todoDao.insert(TodoEntity(task = "Watch nine-nine brooklyn", importance = TodoImportance.DEFAULT, todoDate = DateTime.now().millis, categoryId = 1))
                todoDao.insert(TodoEntity(task = "Read book", importance = TodoImportance.DEFAULT, todoDate = DateTime.now().millis, categoryId = 1))
                todoDao.insert(TodoEntity(task = "Learn english words", importance = TodoImportance.DEFAULT, todoDate = DateTime.now().plusDays(1).millis, categoryId = 1))
                todoDao.insert(TodoEntity(task = "Watch nine-nine brooklyn", importance = TodoImportance.DEFAULT, todoDate = DateTime.now().plusDays(1).millis, categoryId = 1))
                todoDao.insert(TodoEntity(task = "Read book", importance = TodoImportance.DEFAULT, todoDate = DateTime.now().plusDays(1).millis, categoryId = 1))
                todoDao.insert(TodoEntity(task = "Learn english words", importance = TodoImportance.HIGH_IMPORTANCE, todoDate = DateTime.now().plusDays(4).millis, categoryId = 1))
                todoDao.insert(TodoEntity(task = "Watch nine-nine brooklyn", importance = TodoImportance.DEFAULT, todoDate = DateTime.now().plusDays(3).millis, categoryId = 1))
                todoDao.insert(TodoEntity(task = "Read book", importance = TodoImportance.DEFAULT, todoDate = DateTime.now().plusDays(1).millis, categoryId = 1))

                // create todos `Work` category
                todoDao.insert(TodoEntity(task = "Continue Todou project", importance = TodoImportance.HIGH_IMPORTANCE, todoDate = DateTime.now().millis, categoryId = 2))
                todoDao.insert(TodoEntity(task = "Finish Cancel/Payment subscription popup", importance = TodoImportance.DEFAULT, todoDate = DateTime.now().millis, categoryId = 2))
                todoDao.insert(TodoEntity(task = "Repair a stool", importance = TodoImportance.DEFAULT, todoDate = DateTime.now().plusDays(1).millis, categoryId = 2))


                // create todos `Shopping` category
                todoDao.insert(TodoEntity(task = "Buy sneekers", importance = TodoImportance.HIGH_IMPORTANCE, todoDate = DateTime.now().millis, categoryId = 3))
                todoDao.insert(TodoEntity(task = "Buy apartment", importance = TodoImportance.DEFAULT, todoDate = DateTime.now().plusDays(4).millis, categoryId = 3))
                todoDao.insert(TodoEntity(task = "Buy time machine", importance = TodoImportance.DEFAULT, todoDate = DateTime.now().plusDays(1).millis, categoryId = 3))


                // create todos `Routine` category
                todoDao.insert(TodoEntity(task = "Meet with friend", importance = TodoImportance.HIGH_IMPORTANCE, todoDate = DateTime.now().millis, categoryId = 4))
                todoDao.insert(TodoEntity(task = "Read a book", importance = TodoImportance.DEFAULT, todoDate = DateTime.now().plusDays(1).millis, categoryId = 4))
            }
        }
    }
}