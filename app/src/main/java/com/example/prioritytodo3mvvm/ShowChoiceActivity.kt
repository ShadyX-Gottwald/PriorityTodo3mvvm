package com.example.prioritytodo3mvvm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.prioritytodo3mvvm.databinding.ActivityShowChoiceBinding

class ShowChoiceActivity : AppCompatActivity() {
    private  lateinit var bind: ActivityShowChoiceBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityShowChoiceBinding.inflate(layoutInflater)
        setContentView(bind.root)
        registerClicks()
    }

    private fun registerClicks() {

        bind.Fab.setOnClickListener() {
            //popUpFragment = TodoDialogFragment()
            // popUpFragment.listener
            val intent = Intent(this@ShowChoiceActivity,AddTodoActivity::class.java)
            startActivity(intent)

        }

        bind.viewTodosBtn.setOnClickListener() {
            val intent = Intent(this@ShowChoiceActivity,ViewTodosActivity::class.java)
            startActivity(intent)
        }
    }
}