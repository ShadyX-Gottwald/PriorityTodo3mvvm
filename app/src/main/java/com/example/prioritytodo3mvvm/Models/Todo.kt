package com.example.prioritytodo3mvvm.Models

//import java.io.Serializable
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import java.util.UUID
import kotlinx.serialization.serializer



@Parcelize
data class Todo(
    val todoId: String,
    val todo: String,
    val userId: String
) : Parcelable {
    constructor() : this("","","")

    override fun toString(): String {
        return "$todo , $todoId , $userId"
    }
}

fun Todo.toMap():MutableMap<String,String> {

    return mutableMapOf(
        "todoId" to todoId.toString() ,
         "todo" to todo,
        "userId" to userId
    )

}
