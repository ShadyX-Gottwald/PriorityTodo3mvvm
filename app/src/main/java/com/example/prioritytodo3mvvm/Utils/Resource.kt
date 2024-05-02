package com.example.prioritytodo3mvvm.Utils

sealed  class Resource<T>(
    var data: T? = null,
    var message: String? = null

) {
     class Success <T>(data: T ): Resource<T>()
     class Failure<T>(message: String): Resource<T>(message = message)
     class Loading<T>(data: T): Resource<T>()

    class Unspecified<T>(): Resource<T>()
}