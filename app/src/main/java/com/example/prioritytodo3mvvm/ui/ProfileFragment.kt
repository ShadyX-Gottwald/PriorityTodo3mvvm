package com.example.prioritytodo3mvvm.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import com.example.prioritytodo3mvvm.Adapters.ViewUserTodosAdapter
import com.example.prioritytodo3mvvm.Models.Todo
import com.example.prioritytodo3mvvm.R
import com.example.prioritytodo3mvvm.Utils.Resource
import com.example.prioritytodo3mvvm.ViewTodosViewModel
import com.example.prioritytodo3mvvm.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestoreDb: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val viewModel by lazy { ProfileViewModel(auth, firestoreDb) }
    private lateinit var viewTodosAdapter: ViewUserTodosAdapter
    private lateinit var list: MutableList<Todo>

    companion object {
        fun newInstance() = ProfileFragment()
    }

    private fun init() {
        //viewTodoViewModel.todoList = mutableListOf()

        binding.recView.setHasFixedSize(false)
        // viewTodosAdapter = ViewUserTodosAdapter(list,auth,firestoreDb)
        // bind.recView.adapter = viewTodosAdapter

    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.viewModelScope.launch {
            viewModel.result.observe(viewLifecycleOwner, Observer {
                when (it) {
                    is Resource.Loading -> {
                        Toast.makeText(requireContext(), "Loading", Toast.LENGTH_SHORT).show()
                        // bind.recView.adapter = ViewUserTodosAdapter(viewTodoViewModel.result.value!!.data, auth, firestoreDb)
                    }

                    is Resource.Failure -> {}
                    is Resource.Success -> {
                        Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show()
                        viewTodosAdapter =
                            ViewUserTodosAdapter(viewModel.result.value?.data, auth, firestoreDb)
                        binding.recView.adapter = viewTodosAdapter
                    }

                    else -> {
                        Toast.makeText(requireContext(), "Unspecified", Toast.LENGTH_SHORT)
                            .show()

                    }
                }

            })
        }
    }
}