package com.example.toodoo.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.toodoo.R
import com.example.toodoo.data.Todo
import kotlinx.android.synthetic.main.todo_row.view.*
import java.text.SimpleDateFormat
import java.util.*

//onTodoListener: OnTodoListener
class TodoAdapter(val onItemClick: (Todo) -> Unit) : RecyclerView.Adapter<TodoAdapter.ViewHolder>() {
    private var todoList: MutableList<Todo> = mutableListOf()
    //private var mOnTodoListener = onTodoListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.todo_row, parent,false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val todo = todoList[position]
        holder.todoTitleText.text = todo.title

        holder.todoPriorityText.text = when(todo.priority) {
            0 -> "LOW"
            1 -> "MEDIUM"
            2 -> "HIGH"
            else -> "NONE"
        }
        holder.todoStatusText.text = when(todo.status) {
            0 -> "UNFINISHED"
            1 -> "FINISHED"
            else -> "NONE"
        }

        val date: Date = todo.date
        val formatter = SimpleDateFormat("dd-MM-yyyy HH:mm:ss")

        holder.todoDateText.text = formatter.format(date)
    }

    fun getSingleTodo(position: Int): Todo {
        return todoList[position]
    }

    override fun getItemCount(): Int {
        return todoList.size
    }

    override fun getItemId(position: Int): Long {
        return todoList[position].id
    }

    fun setTodos(todos: List<Todo>) {
        this.todoList = todos as MutableList<Todo>
        notifyDataSetChanged()
    }

    fun addTodo(position: Int, todo: Todo) {
        this.todoList.add(position, todo)
        notifyItemInserted(position)
    }

    fun removeTodo(position: Int) {
        this.todoList.removeAt(position)
        notifyItemRemoved(position)
    }

    // var onNoteListener: OnTodoListener
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val todoTitleText: TextView = view.findViewById(R.id.todoTitle)
        val todoPriorityText: TextView = view.findViewById(R.id.todoPriority)
        val todoStatusText: TextView = view.findViewById(R.id.todoStatus)
        val todoDateText: TextView = view.findViewById(R.id.todoDate)

        init {
            view.setOnClickListener {
                if(adapterPosition != RecyclerView.NO_POSITION) {
                    onItemClick(todoList[adapterPosition])
                }
            }
        }

//        init {
//            view.setOnClickListener(this)
//        }
//
//        override fun onClick(v: View?) {
//            onNoteListener.onTodoClick(adapterPosition)
//        }

    }

    interface OnTodoListener {
        fun onTodoClick(position: Int)
    }
}