package com.dungngminh.jetpack_2

import androidx.lifecycle.*
import com.dungngminh.jetpack_2.model.ToDo
import com.dungngminh.jetpack_2.repository.TodoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: TodoRepository): ViewModel() {

    val listTodos : Flow<List<ToDo>> = flow {
        repository.getAllTasks().collect {
            emit(it)
        }
    }

    suspend fun insertTodo(toDo: ToDo){
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertTodo(toDo)
        }
    }
}