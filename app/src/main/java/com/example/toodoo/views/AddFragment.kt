package com.example.toodoo.views

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.toodoo.R
import com.example.toodoo.data.Todo
import kotlinx.android.synthetic.main.fragment_add.*
import java.text.SimpleDateFormat
import java.util.*


class AddFragment : Fragment(), View.OnClickListener {
    private var listener: OnFragmentInteractionListener? = null
    var dataPasser: OnDataPass? = null

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_add, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val addButton = view?.findViewById(R.id.btn_Add) as Button
        addButton.setOnClickListener(this)

        val cancelButton = view?.findViewById(R.id.btn_Cancel) as Button
        cancelButton.setOnClickListener(this)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) listener = context else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }
        dataPasser = context as OnDataPass
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(uri: Uri)
    }

    interface OnDataPass {
        fun onDataPass(data: Todo?)
    }

    companion object {
        @JvmStatic
        fun newInstance(): AddFragment{
            return AddFragment()
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_Add -> {
                val title: String = editText_TodoTitle.text.toString()
                if (title != "") {
                    val description: String = editText_comment.text.toString()
                    val priority: Int = spinner_priority.selectedItemPosition
                    val date = Date(System.currentTimeMillis())

                    var todo: Todo = Todo(title, priority, 0, date, description)

                    dataPasser?.onDataPass(todo)

                    fragmentManager!!.popBackStack()
                }
            }
            R.id.btn_Cancel -> {
                fragmentManager!!.popBackStack()
            }
        }
    }
}
