package com.example.prioritytodo3mvvm

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.prioritytodo3mvvm.Models.Person
import com.example.prioritytodo3mvvm.Models.asMap
import com.example.prioritytodo3mvvm.databinding.ActivityImageUploadBinding
import com.google.android.gms.tasks.Task
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.google.firebase.storage.UploadTask.*
import com.google.firebase.storage.storage

import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID
import kotlin.coroutines.Continuation

class ImageUploadActivity : AppCompatActivity() {
    private lateinit var bind: ActivityImageUploadBinding
    private val PICK_IMAGE_REQUEST = 71
    private var filePath: Uri? = null
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestoreDb: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var storageReference: StorageReference? = FirebaseStorage.getInstance().reference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityImageUploadBinding.inflate(layoutInflater)
        setContentView(bind.root)
        clickListeners()

    }

    private fun clickListeners() {
        bind.btnSelect.setOnClickListener {
            selectImage()

        }
        bind.btnUpload.setOnClickListener {
            uploadImage()
        }
    }

    private fun selectImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent,
            100)

    }
    private fun uploadImage(){
        val formatter = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss" , Locale.ENGLISH)
        val now = Date()
        val file = formatter.format(now)

        if(filePath != null){
            val ref = storageReference?.child("uploads/$file")
            val uploadTask = ref?.putFile(filePath!!)

            val urlTask = uploadTask?.continueWithTask(com.google.android.gms.tasks.Continuation<TaskSnapshot, Task<Uri>> { task ->
                if (!task.isSuccessful) {
                    task.exception?.let {
                        throw it
                    }
                }
                return@Continuation ref.downloadUrl
            })?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val downloadUri = task.result
                    //addUploadRecordToDb(downloadUri.toString())
                    addPerson(downloadUri.toString())

                } else {
                    // Handle failures
                }
            }?.addOnFailureListener{

            }
        }else{
            Toast.makeText(this, "Please Upload an Image", Toast.LENGTH_SHORT).show()
        }
    }


    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && data != null && data.data != null ) {

            filePath = data.data
          bind.imageView.setImageURI(filePath)
        }
    }
    private fun addUploadRecordToDb(uri: String){
        val db = FirebaseFirestore.getInstance()

        val data = HashMap<String, Any>()
        data["imageUrl"] = uri

        db.collection("posts1")
            .add(data)
            .addOnSuccessListener { documentReference ->
                Toast.makeText(this, "Saved to DB", Toast.LENGTH_LONG).show()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error saving to DB", Toast.LENGTH_LONG).show()
            }
    }


    private fun addPerson(Url: String) {
        val db = FirebaseFirestore.getInstance()

        val name = bind.etName.text.toString()

        val objPerson = Person(name , Url)

        db.collection("posts1")
            .add(objPerson.asMap())
            .addOnSuccessListener { documentReference ->
                Toast.makeText(this, "Saved to DB", Toast.LENGTH_LONG).show()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error saving to DB", Toast.LENGTH_LONG).show()
            }



    }
}