package com.example.prioritytodo3mvvm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.prioritytodo3mvvm.Models.Todo
import com.example.prioritytodo3mvvm.Utils.Resource
import com.example.prioritytodo3mvvm.databinding.ActivityAddTodoBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.UUID

class AddTodoActivity : AppCompatActivity() {
    private lateinit var bind: ActivityAddTodoBinding
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestoreDb: FirebaseFirestore  = FirebaseFirestore.getInstance()
    private val addTodoViewModel by lazy {
        com.example.prioritytodo3mvvm.AddTodoViewModel(
            auth,
            firestoreDb
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bind = ActivityAddTodoBinding.inflate(layoutInflater)
        setContentView(bind.root)
        registerEvents()
    }


    fun registerEvents() {
        bind.addBtn.setOnClickListener() {
            //Get params
            // Create Obj
            val todoText = bind.todoEt.text.toString()
            val user = auth.currentUser!!.uid
            val todoId = UUID.randomUUID()

            val todo: Todo = Todo(todoId.toString(),todoText ,user )

            addTodoViewModel.AddTodo(todo)
            when(val res = addTodoViewModel.result.value){
                is Resource.Loading -> {}
                is Resource.Success -> {
                    Toast.makeText(this@AddTodoActivity ,res.message, Toast.LENGTH_SHORT ).show()
                }
                else -> {}
            }


        }
    }
}