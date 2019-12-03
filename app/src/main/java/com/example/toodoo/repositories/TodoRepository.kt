package com.example.toodoo.repositories

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.example.toodoo.data.Todo
import com.example.toodoo.data.TodoDataBase
import com.example.toodoo.data.TodoDAO
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import kotlin.random.Random

class TodoRepository (application: Application) {
    private var todoDao: TodoDAO
    private var allTodos: LiveData<List<Todo>>

    init {
        val db: TodoDataBase = TodoDataBase.getInstance(application.applicationContext)!!
        todoDao = db.todoDao()
        allTodos = todoDao.getAllTodos()
    }

    fun insert(t: Todo) = CoroutineScope(Dispatchers.IO).launch {
        todoDao.insertTodo(t)
    }

    fun update(t: Todo) = CoroutineScope(Dispatchers.IO).launch {
        todoDao.updateTodo(t)
    }

    fun delete(t: Todo) = CoroutineScope(Dispatchers.IO).launch {
        todoDao.deleteTodo(t)
    }

    fun deleteAllTodos() = CoroutineScope(Dispatchers.IO).launch {
        todoDao.deleteAllTodos()
    }

    fun getAllTodos(): LiveData<List<Todo>> {
        return allTodos
    }
}