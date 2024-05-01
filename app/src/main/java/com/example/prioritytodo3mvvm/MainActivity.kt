package com.example.prioritytodo3mvvm

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.prioritytodo3mvvm.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth : FirebaseAuth
    private val viewModel by lazy { RegisterViewModel(auth ) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        registerEvents()
        auth = FirebaseAuth.getInstance()

    }

    private fun registerEvents() {

        binding.login.setOnClickListener() {
            //navController.navigate(R.id.action_signUp_Fragment_to_loginFragment)
            val intent: Intent = Intent(this@MainActivity, LoginActivity::class.java)
            startActivity(intent)
        }



        binding.nextBtn.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val pass = binding.password.text.toString().trim()
            val verifyPass = binding.etPassConfirm.text.toString().trim()

            if (email.isNotEmpty() && pass.isNotEmpty() && verifyPass.isNotEmpty()) {
                binding.progressCircular.visibility = View.VISIBLE
                if (pass.equals(verifyPass)) {
                    auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener() {
                        if (it.isSuccessful) {

                            Toast.makeText(
                                this@MainActivity,
                                "$email Registered SuccessFully",
                                Toast.LENGTH_LONG
                            ).show()
                            val intent: Intent =
                                Intent(this@MainActivity, LoginActivity::class.java)
                            startActivity(intent)

                        } else {
                            Toast.makeText(
                                this@MainActivity,
                                it.exception?.message.toString(),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                } else {
                    Toast.makeText(this@MainActivity, "Password Does Not Match", Toast.LENGTH_SHORT)
                        .show()

                }
                binding.progressCircular.visibility = View.GONE

            } else {
                Toast.makeText(this@MainActivity, "Fields Cannot Be Empty", Toast.LENGTH_SHORT)
                    .show()

            }
        }
    }

}