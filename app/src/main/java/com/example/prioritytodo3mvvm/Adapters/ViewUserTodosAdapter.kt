package com.example.prioritytodo3mvvm.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.prioritytodo3mvvm.Models.Todo
import com.example.prioritytodo3mvvm.databinding.EachTodoBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ViewUserTodosAdapter(
    private val list: List<Todo>?,
    private val auth:  FirebaseAuth,
    private val firestoreDB: FirebaseFirestore
)
    : RecyclerView.Adapter<ViewUserTodosAdapter.ViewUserTodosAdapterViewHolder>()
{
    private lateinit var bind: EachTodoBinding
    inner class ViewUserTodosAdapterViewHolder(eachTodoBinding: EachTodoBinding):
        RecyclerView.ViewHolder(eachTodoBinding.root) {}

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewUserTodosAdapterViewHolder {
        val binding = EachTodoBinding
            .inflate(LayoutInflater.from(parent.context), parent , false)

        bind = binding
        return ViewUserTodosAdapterViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list!!.count()
    }

    override fun onBindViewHolder(holder: ViewUserTodosAdapterViewHolder, position: Int) {
        val todoItem = list!![position]
        bind.materialTextView.text = todoItem.todo
    }
}