package com.example.prioritytodo3mvvm

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.Collections.synchronizedList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prioritytodo3mvvm.Models.Todo
import com.example.prioritytodo3mvvm.Utils.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.model.Document
import com.google.firebase.firestore.toObject
import com.google.firebase.firestore.toObjects
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID

const val TAG = "VIEW TODOS VM"

class ViewTodosViewModel(
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


    fun getUserTodos() {

        // mlist.value?.clear()

        //Get data by user Id
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
                        //val todoObj = Todo(UUID.fromString(todoId), todo , userId.toString())

                        // Log.d(TAG, todoObj.toString())
                        //Add To list To Display

                        //TaskLIst.add(todoObj)
                        // Log.d(TAG , mlist.value!!.count().toString())
                    }
                    //Return Result

                    // Log.d(TAG , mlist.value!!.joinToString() )

                }

            }.addOnFailureListener {
                // result.value = Resource.Failure(it.message.toString())

            }
    }

    fun getUserDataSimple() {

        firestoreDb.collection("Todos1")
            .where(Filter.equalTo("userId", auth.currentUser!!.uid))
            .get().addOnSuccessListener {
                val data =   it.toObjects(Todo::class.java).toMutableList()

                Log.d(TAG , data.size.toString())
                Log.d(TAG , data.joinToString { it.todo })
                result.postValue(Resource.Success(data))


            }.addOnFailureListener {


            }

    }

}