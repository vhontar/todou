package com.easycoding.todou.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.easycoding.todou.data.database.TODOU_DATABASE_NAME
import com.easycoding.todou.data.database.TodouDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideTodouDatabase(@ApplicationContext context: Context): TodouDatabase =
        Room.databaseBuilder(context, TodouDatabase::class.java, TODOU_DATABASE_NAME)
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)

                    // TODO reflection to setup defaults data
                }
            })
            .build()

    @Singleton
    @Provides
    fun provideTodoDao(todouDatabase: TodouDatabase) = todouDatabase.getTodoDao()

    @Singleton
    @Provides
    fun provideCategoryDao(todouDatabase: TodouDatabase) = todouDatabase.getCategoryDao()

    @Singleton
    @Provides
    fun provideCategoryWithTodosDao(todouDatabase: TodouDatabase) = todouDatabase.getCategoryWithTodosDao()

}