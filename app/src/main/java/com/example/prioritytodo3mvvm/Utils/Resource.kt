package com.example.prioritytodo3mvvm.Utils

sealed  class Resource<T>(
    val data: T? = null,
    val message: String? = null

) {
     class Success <T>(data: T ): Resource<T>()
     class Failure<T>(message: String): Resource<T>(message = message)
     class Loading<T>(): Resource<T>()
}