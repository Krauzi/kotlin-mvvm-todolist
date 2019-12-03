package com.example.toodoo.views

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.toodoo.R
import com.example.toodoo.data.Todo
import com.example.toodoo.viewmodels.TodoViewModel

import kotlinx.android.synthetic.main.activity_edit.*

class EditActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var todo_Note: Todo
    private lateinit var todoViewModel: TodoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        todoViewModel = ViewModelProviders.of(this).get(TodoViewModel::class.java)

        btn_Edit.setOnClickListener(this)
        btn_Delete.setOnClickListener(this)
    }

    override fun onResume() {
        super.onResume()

        todo_Note = intent.getSerializableExtra("TODO_DATA") as Todo

        editText_EditTitle.setText(todo_Note.title)
        spinner_edit_priority.setSelection(todo_Note.priority)
        editText_edit_comment.setText(todo_Note.comment)
    }

    override fun onClick(v: View?) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Confirmation")

        when(v?.id) {
            R.id.btn_Edit -> {
                builder.setMessage("Do you want to edit this record?")
                builder.setPositiveButton("YES"){_, _ ->
                    todo_Note.title = editText_EditTitle.text.toString()
                    todo_Note.priority = spinner_edit_priority.selectedItemPosition
                    todo_Note.comment = editText_edit_comment.text.toString()

                    if (spinner_edit_status.selectedItemPosition == 1) {
                        todoViewModel.delete(todo_Note)
                        this.returnHome("Record has been removed.")
                    } else {
                        todoViewModel.update(todo_Note)
                        this.returnHome("Successfully edited record.")
                    }
                }
                builder.setNegativeButton("No") {_, _ -> }

                val dialog: AlertDialog = builder.create()

                dialog.show()
            }
            R.id.btn_Delete -> {
                builder.setMessage("Do you want to remove this record?")
                builder.setPositiveButton("YES"){_, _ ->
                    todoViewModel = ViewModelProviders.of(this).get(TodoViewModel::class.java)

                    todoViewModel.delete(todo_Note)
                    this.returnHome("Record has been removed.")
                }
                builder.setNegativeButton("No") {_, _ -> }

                val dialog: AlertDialog = builder.create()

                dialog.show()
            }
        }
    }

    private fun returnHome(info: String) {
        val returnHome = Intent(applicationContext, MainActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).apply {
            putExtra("RESULT_INFO", info)
        }

        setResult(Activity.RESULT_OK, returnHome)

        finish()
    }
}
