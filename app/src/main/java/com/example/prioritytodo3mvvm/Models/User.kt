package com.example.prioritytodo3mvvm.Models

data class User(
    val userId: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val imagePath: String = "",

) {
    constructor() : this("","" , "", "")
}
