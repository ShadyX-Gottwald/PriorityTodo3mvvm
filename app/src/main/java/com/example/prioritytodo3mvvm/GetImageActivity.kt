package com.example.prioritytodo3mvvm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.prioritytodo3mvvm.databinding.ActivityGetImageBinding
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

class GetImageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGetImageBinding
    private val firestoreDb: FirebaseFirestore = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGetImageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getImage().addOnSuccessListener {
            //val obj = it.
            Glide.with(this).load(it.documents[0]["imageUrl"])
                .into(binding.imageView)

            Toast.makeText(this, " Getting Image " , Toast.LENGTH_SHORT)
                .show()


        }.addOnFailureListener {
            binding.imageView.setImageURI(null)
            Toast.makeText(this, "Error Getting Image " , Toast.LENGTH_SHORT)
                .show()
        }
    }

    //Get Image Logic
    private fun getImage(): Task<QuerySnapshot> =
        firestoreDb.collection("posts")
            .limit(1)
        .get()
}