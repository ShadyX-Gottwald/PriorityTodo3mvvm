package com.example.prioritytodo3mvvm.Models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Person(
    val name: String ,
    val imgUrl: String,
) : Parcelable {

    constructor(): this("" ,"")


}

fun Person.asMap(): Map<String, Any> {
    val map = mapOf<String,Any>(
        "name" to name,
        "imgUrl" to imgUrl


    )
    return map
}
