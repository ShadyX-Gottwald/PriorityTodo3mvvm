package com.example.prioritytodo3mvvm.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.prioritytodo3mvvm.Adapters.ViewUserTodosAdapter
import com.example.prioritytodo3mvvm.Models.Todo
import com.example.prioritytodo3mvvm.R
import com.example.prioritytodo3mvvm.TAG
import com.example.prioritytodo3mvvm.ViewTodosViewModel
import com.example.prioritytodo3mvvm.databinding.FragmentViewUserTodoBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.FirebaseFirestore
import java.util.UUID

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ViewUserTodoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
private const val TAG = "FRAGMENT VIEW TODOS"
class ViewUserTodoFragment : Fragment() {
    private lateinit var bind: FragmentViewUserTodoBinding
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestoreDb: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val viewTodoViewModel by lazy { ViewTodosViewModel(auth, firestoreDb) }
    private lateinit var list: MutableList<Todo>
    private lateinit var viewUserTodosAdapter: ViewUserTodosAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        bind = FragmentViewUserTodoBinding.inflate(inflater,container,false)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getUserTodos()
    }

    private fun init() {
        list = mutableListOf()
        bind.recView.setHasFixedSize(false)


        viewUserTodosAdapter = ViewUserTodosAdapter(list,auth,firestoreDb)
        bind.recView.adapter = viewUserTodosAdapter

    }

    private fun getUserTodos() {
        //viewTodoViewModel.getUserTodosAsync()
        list.clear()
        firestoreDb.collection("Todos1")
            .where(Filter.equalTo("userId", auth.currentUser!!.uid))
            .get().addOnCompleteListener { doc ->
                Log.d(TAG, "Getting Data")
                if (doc.isSuccessful) {
                    Log.d("VIEW MODEL", "Getting Data Success")
                    val data = doc.result

                    for (each in data) {
                        Log.d(TAG, each.data.toString())


                        //Get Fields
                        val todo = each.data["todo"].toString()
                        val userId = each.data["userId"]
                        val todoId = each.data["todoId"].toString()

                        //Create Obj to Save
                        val todoObj = Todo(UUID.fromString(todoId).toString(), todo , userId.toString())

                         Log.d(TAG, todoObj.toString())
                        //Add To list To Display

                        list.add(todoObj)
                        // Log.d(TAG , mlist.value!!.count().toString())
                    }

                }

            }.addOnFailureListener {
                // result.value = Resource.Failure(it.message.toString())

            }

    }

}