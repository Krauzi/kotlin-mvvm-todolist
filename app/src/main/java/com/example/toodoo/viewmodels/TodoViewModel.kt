package com.example.toodoo.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.toodoo.data.Todo
import com.example.toodoo.repositories.TodoRepository

class TodoViewModel(application: Application) : AndroidViewModel(application) {

    private var repository: TodoRepository = TodoRepository(application)
    private var allTodos: LiveData<List<Todo>> = repository.getAllTodos()

    fun insert(activity_todo: Todo) {
        repository.insert(activity_todo)
    }

    fun update(activity_todo: Todo) {
        repository.update(activity_todo)
    }

    fun delete(activity_todo: Todo) {
        repository.delete(activity_todo)
    }

    fun deleteAllTodos() {
        repository.deleteAllTodos()
    }

    fun getAllTodos(): LiveData<List<Todo>> {
        return allTodos
    }
}