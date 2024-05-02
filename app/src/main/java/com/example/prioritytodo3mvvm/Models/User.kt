package com.example.prioritytodo3mvvm.Models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class User(
    val userId: String,
    val password: String,
    val email: String,


) : Parcelable {
    constructor() : this("","" , "",)


}
fun User.toMap():Map<String,String> {
    val map: Map<String, String> = mapOf("email" to email,
        "password" to password ,
        "userId" to userId

    )
    return map

}
