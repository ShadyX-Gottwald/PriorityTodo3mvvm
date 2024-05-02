package com.example.prioritytodo3mvvm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import com.example.prioritytodo3mvvm.Adapters.ViewUserTodosAdapter
import com.example.prioritytodo3mvvm.Models.Todo
import com.example.prioritytodo3mvvm.Utils.Resource
import com.example.prioritytodo3mvvm.databinding.ActivityViewTodosBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID

class ViewTodosActivity : AppCompatActivity() {
    private lateinit var bind: ActivityViewTodosBinding
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestoreDb: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val viewTodoViewModel by lazy { ViewTodosViewModel(auth, firestoreDb) }
    private lateinit var viewTodosAdapter: ViewUserTodosAdapter
    private lateinit var list: MutableList<Todo>
    private var name: CoroutineName = CoroutineName("VIEW TODO SCOPE")
    private var scope: CoroutineScope = CoroutineScope(name)


    private fun init() {
        //viewTodoViewModel.todoList = mutableListOf()

        bind.recView.setHasFixedSize(false)
       // viewTodosAdapter = ViewUserTodosAdapter(list,auth,firestoreDb)
       // bind.recView.adapter = viewTodosAdapter

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        list = mutableListOf()
        bind = ActivityViewTodosBinding.inflate(layoutInflater)

        //getUserTodos()
        setContentView(bind.root)
        init()
        val viewModel by lazy { ViewTodosViewModel(auth,firestoreDb) }
        //getUserTodos()
        //init()


        viewModel.viewModelScope.launch {
            viewModel.result.observe(this@ViewTodosActivity, Observer {
                when (it) {
                    is Resource.Loading -> {
                       // Toast.makeText(this@ViewTodosActivity, "Loading", Toast.LENGTH_SHORT).show()
                        // bind.recView.adapter = ViewUserTodosAdapter(viewTodoViewModel.result.value!!.data, auth, firestoreDb)
                    }

                    is Resource.Failure -> {}
                    is Resource.Success -> {
                        Toast.makeText(this@ViewTodosActivity, "Success", Toast.LENGTH_SHORT).show()
                    }

                    else -> {
                        Toast.makeText(this@ViewTodosActivity, "Unspecified", Toast.LENGTH_SHORT)
                            .show()

                        //viewTodoViewModel.getUserTodosAsync()
                        Log.d(TAG, viewTodoViewModel.result.value?.data.toString())
                        Log.d(TAG, "Called inside ViewTodosActivity")
                        Log.d(TAG, viewTodoViewModel.datalist.value?.size.toString())



                    }
                }
                // bind.recView.adapter = ViewUserTodosAdapter(viewTodoViewModel.datalist.value, auth, firestoreDb)

           })

        }

    }

    private fun getUserTodos() {
       // list = mutableListOf()
        firestoreDb.collection("Todos1")
            .where(Filter.equalTo("userId", auth.currentUser!!.uid))
            .get().addOnSuccessListener {

              val data = it.toObjects(Todo::class.java).toMutableList()

                Log.d(TAG , data.size.toString())
                Log.d(TAG , data.joinToString { it.todo })
                list = data
                //Init Recycler View Adapter
                viewTodosAdapter = ViewUserTodosAdapter(data,auth,firestoreDb)
                bind.recView.adapter = viewTodosAdapter



            }.addOnFailureListener {
                Log.d(TAG , "FAILURE Getting Data")

            }


    }


    private fun dummyList(): MutableList<Todo> {

        val list = mutableListOf<Todo>().apply {
            add(Todo(UUID.randomUUID().toString(), "Task1", UUID.randomUUID().toString()))
            add(Todo(UUID.randomUUID().toString(), "Task2", UUID.randomUUID().toString()))

        }

        return list

    }


}
