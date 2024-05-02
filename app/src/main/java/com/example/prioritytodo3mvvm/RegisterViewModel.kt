package com.example.prioritytodo3mvvm

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prioritytodo3mvvm.Models.RegisterModel
import com.example.prioritytodo3mvvm.Utils.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val auth: FirebaseAuth,

): ViewModel() {

    // Register State
     val _registerState = MutableLiveData<Resource<FirebaseUser>>(Resource.Unspecified()).apply {
        this.value = Resource.Unspecified()
    }

    //UI component data
    val registerFields = MutableLiveData<RegisterModel>().apply {
        this.value = RegisterModel()
    }


    fun createAccountWith() {

        // Setup user data to submit to Register
        val user = RegisterModel(registerFields.value!!.email
            , registerFields.value!!.password, registerFields.value!!.passConfirm)

        viewModelScope.launch {


        }


//        auth.createUserWithEmailAndPassword(user.email, user.password).addOnCompleteListener{
//            if(it.isSuccessful) {
//                it.result.user.let { user ->
//
//                }
//
//            }
//        }.addOnFailureListener {
//
//
//        }
//
    }

}