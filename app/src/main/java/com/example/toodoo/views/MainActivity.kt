package com.example.toodoo.views

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProviders
import com.example.toodoo.R
import com.example.toodoo.data.Todo
import com.example.toodoo.viewmodels.TodoViewModel
import kotlinx.android.synthetic.main.activity_main.*

const val INFO_RESULT_REQUEST = 1001

class MainActivity : AppCompatActivity(),
    AddFragment.OnFragmentInteractionListener, NotesFragment.OnFragmentInteractionListener,
    AddFragment.OnDataPass
{
    private val BACK_STACK_ROOT_TAG = "root_fragment"
    private lateinit var todoViewModel: TodoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        todoViewModel = ViewModelProviders.of(this).get(TodoViewModel::class.java)

        var fragmentManager: FragmentManager = supportFragmentManager
        fragmentManager.beginTransaction()
            .replace(R.id.container, NotesFragment.newInstance())
            .commit()

    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var fragmentManager: FragmentManager = supportFragmentManager
        fragmentManager.popBackStack(BACK_STACK_ROOT_TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE)

        when (item.itemId) {
            R.id.action_add -> {
                fragmentManager.beginTransaction()
                    .replace(R.id.container, AddFragment.newInstance())
                    .addToBackStack(BACK_STACK_ROOT_TAG)
                    .commit()
            }
        }
        return true
    }

    override fun onDataPass(data: Todo?) {
        if (data != null) {
            todoViewModel.insert(data)
        }
    }

    override fun onFragmentInteraction(uri: Uri) {
        Log.d("Fragments", "On fragment interaction")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == INFO_RESULT_REQUEST && resultCode == Activity.RESULT_OK) {
                Toast.makeText(this, data?.getStringExtra("RESULT_INFO").toString(), Toast.LENGTH_LONG).show()
        }
    }
}
