package com.example.toodoo.views

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.toodoo.R
import com.example.toodoo.adapters.TodoAdapter
import com.example.toodoo.data.Todo
import com.example.toodoo.viewmodels.TodoViewModel
import kotlinx.android.synthetic.main.todo_row.*

class NotesFragment : Fragment() {
    private var listener: OnFragmentInteractionListener? = null

    private lateinit var todoViewModel: TodoViewModel
    lateinit var myAdapter: TodoAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_notes, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        recyclerView = view!!.findViewById(R.id.RecyclerView_notes)

        recyclerLoad()

        todoViewModel = ViewModelProviders.of(this).get(TodoViewModel::class.java)
        todoViewModel.getAllTodos().observe(this, Observer<List<Todo>> {
                t -> myAdapter.setTodos(t!!)
        })
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) listener = context else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        @JvmStatic
        fun newInstance(): NotesFragment {
            return NotesFragment()
        }
    }

    private fun recyclerLoad() {
        myAdapter = TodoAdapter {
            val intent = Intent(activity, EditActivity::class.java).apply {
                putExtra("TODO_DATA", it )
            }

            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivityForResult(intent, INFO_RESULT_REQUEST)
        }
        recyclerView.apply{
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL,false)
            hasFixedSize()
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            itemAnimator = DefaultItemAnimator()
            adapter = myAdapter
        }
    }
}
