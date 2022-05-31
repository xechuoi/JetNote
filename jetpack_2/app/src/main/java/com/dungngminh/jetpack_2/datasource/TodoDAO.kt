package com.dungngminh.jetpack_2.datasource

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.dungngminh.jetpack_2.model.ToDo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged


@Dao
interface TodoDAO {

    @Query("SELECT * FROM todos ORDER BY todos.data_added DESC")
    fun getAllTodos(): Flow<List<ToDo>>
    fun getAllTodosDistinctUntilChanged() = getAllTodos().distinctUntilChanged()

    @Insert
    suspend fun insertTodo(todo: ToDo)
}