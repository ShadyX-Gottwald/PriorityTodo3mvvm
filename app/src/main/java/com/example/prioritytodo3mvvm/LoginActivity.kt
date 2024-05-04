package com.example.prioritytodo3mvvm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.prioritytodo3mvvm.Models.User
import com.example.prioritytodo3mvvm.Models.toMap
import com.example.prioritytodo3mvvm.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var firestoreDb: FirebaseFirestore
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        firestoreDb = FirebaseFirestore.getInstance()
        registerEvents()
    }

    private fun registerEvents() {

        binding.signUp.setOnClickListener {
            //navController.navigate(R.id.action_signInFragment_to_signUpFragment)
        }
        binding.loginBtn.setOnClickListener{
            val email = binding.etEmail.text.toString() .trim()
            val pass = binding.password.text.toString().trim()


            if(email.isNotEmpty() && pass.isNotEmpty() ){
                //Show Progress bar When Logging In
                binding.progressCircular.visibility = View.VISIBLE

                //Use Auth and Results of Those Actions
                auth.signInWithEmailAndPassword(email ,pass).addOnCompleteListener() {
                    if(it.isSuccessful) {
                        Toast.makeText(this@LoginActivity ,"$email Logged In SuccessFully", Toast.LENGTH_LONG).show()
                        //fireStoreAddUser()
                        val intent = Intent(this@LoginActivity,UserDrawerActivity::class.java)
                        startActivity(intent)
                        finish()
                    }else {
                        Toast.makeText(this@LoginActivity, it.exception?.message.toString(), Toast.LENGTH_SHORT).show()
                    }
                    binding.progressCircular.visibility = View.GONE
                }
            }else {
                Toast.makeText(this@LoginActivity , "Empty Fields Not Allowed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun  fireStoreAddUser() {
       val email =  binding.etEmail.text.toString()
        val password = binding.password.text.toString()

        val user: User = User(auth.currentUser!!.uid , email , password)

        firestoreDb.collection("users3").add(user.toMap()).addOnCompleteListener{
            if(it.isSuccessful) Log.d("ADD USER" , "$email user added")
        }

    }
}