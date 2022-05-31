package com.dungngminh.jetpack_2.datasource

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.dungngminh.jetpack_2.model.ToDo
import com.dungngminh.jetpack_2.utils.DateConverter

@Database(entities = [ToDo::class], version = 2, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDAO
}