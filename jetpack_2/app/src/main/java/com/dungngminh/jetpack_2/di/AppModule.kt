package com.dungngminh.jetpack_2.di

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.dungngminh.jetpack_2.datasource.AppDatabase
import com.dungngminh.jetpack_2.datasource.TodoDAO
import com.dungngminh.jetpack_2.utils.DateConverter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideDb(@ApplicationContext context: Context): AppDatabase {
        return Room
            .databaseBuilder(context, AppDatabase::class.java, "todo.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideTodoDAO(db: AppDatabase): TodoDAO {
        return db.todoDao()
    }

}