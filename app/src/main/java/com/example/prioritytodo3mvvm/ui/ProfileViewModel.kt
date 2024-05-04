package com.example.prioritytodo3mvvm.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.prioritytodo3mvvm.Models.Todo
import com.example.prioritytodo3mvvm.TAG
import com.example.prioritytodo3mvvm.Utils.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.FirebaseFirestore

class ProfileViewModel (
    private val auth: FirebaseAuth,
    private val firestoreDb: FirebaseFirestore
) : ViewModel() {

    init {
        getUserDataSimple()
    }
    val datalist = MutableLiveData<MutableList<Todo>>()

    // Init State of View model
    private val _result: MutableLiveData<Resource<List<Todo>>> = MutableLiveData(Resource.Unspecified())


    val result = _result


    var todoList: MutableList<Todo> = mutableListOf()

    fun getUserDataSimple() {

        firestoreDb.collection("Todos1")
            .where(Filter.equalTo("userId", auth.currentUser!!.uid))
            .get().addOnSuccessListener {
                val data =   it.toObjects(Todo::class.java).toMutableList()

                Log.d(TAG , data.size.toString())
                Log.d(TAG , data.joinToString { it.todo })
                result.value = Resource.Success(data)
                result.value?.data?.joinToString { it.todo }?.let { it1 -> Log.d(TAG , it1) }

            }.addOnFailureListener {


            }

    }

}