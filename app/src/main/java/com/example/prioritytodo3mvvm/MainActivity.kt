package com.example.prioritytodo3mvvm

import android.os.Bundle
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
        auth = FirebaseAuth.getInstance()

    }

}