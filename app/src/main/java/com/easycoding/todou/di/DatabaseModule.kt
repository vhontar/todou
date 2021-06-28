package com.easycoding.todou.di

import android.content.Context
import androidx.room.Room
import com.easycoding.todou.data.database.TODOU_DATABASE_NAME
import com.easycoding.todou.data.database.TodouDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideTodouDatabase(@ApplicationContext context: Context, callback: TodouDatabase.Callback): TodouDatabase =
        Room.databaseBuilder(context, TodouDatabase::class.java, TODOU_DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .addCallback(callback)
            .build()

    @Provides // don't need to be a singleton because room already returns the same instance
    fun provideTodoDao(todouDatabase: TodouDatabase) = todouDatabase.getTodoDao()

    @Provides // don't need to be a singleton because room already returns the same instance
    fun provideCategoryDao(todouDatabase: TodouDatabase) = todouDatabase.getCategoryDao()

    @Provides // don't need to be a singleton because room already returns the same instance
    fun provideCategoryWithTodosDao(todouDatabase: TodouDatabase) = todouDatabase.getCategoriesWithTodosDao()

    @ApplicationScope
    @Singleton
    @Provides
    fun provideApplicationScope() = CoroutineScope(SupervisorJob())
}

@Qualifier
annotation class ApplicationScope