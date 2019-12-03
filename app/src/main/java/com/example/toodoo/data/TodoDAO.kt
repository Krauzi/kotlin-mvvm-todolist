package com.example.toodoo.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TodoDAO {
    @Query("SELECT * FROM todo_table ORDER BY priority DESC, date DESC")
    fun getAllTodos(): LiveData<List<Todo>>

    @Insert
    suspend fun insertTodo(t: Todo)

    @Update
    suspend fun updateTodo(t: Todo)

    @Delete
    suspend fun deleteTodo(t: Todo)

    @Query("DELETE FROM todo_table")
    suspend fun deleteAllTodos()
}