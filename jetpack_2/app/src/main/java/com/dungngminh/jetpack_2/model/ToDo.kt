package com.dungngminh.jetpack_2.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "todos")
data class ToDo(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,

    val title: String,

    val description: String,

    @ColumnInfo(name = "data_added")
    val dateAdded: Date,
)