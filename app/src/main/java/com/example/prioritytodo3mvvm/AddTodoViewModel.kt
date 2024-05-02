package com.example.prioritytodo3mvvm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.prioritytodo3mvvm.Models.Todo
import com.example.prioritytodo3mvvm.Models.toMap
import com.example.prioritytodo3mvvm.Utils.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AddTodoViewModel(
    private var auth: FirebaseAuth,
    private var firestoreDb: FirebaseFirestore

): ViewModel() {

    val result = MutableLiveData<Resource<Todo>>() .apply {
        this.value = Resource.Loading(Todo())
    }

    fun AddTodo(todo: Todo) {

        firestoreDb.collection("Todos1")
            .add(todo.toMap())
            .addOnCompleteListener{
                if(it.isSuccessful) {
                    result.value = Resource.Success(todo).apply {
                        this.data = todo
                        this.message = "${todo.todo} Success"
                    }
                }
            }.addOnCompleteListener(){

            }

    }






}