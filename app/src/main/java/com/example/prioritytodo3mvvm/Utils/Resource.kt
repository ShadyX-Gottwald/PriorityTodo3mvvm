package com.example.prioritytodo3mvvm.Utils

sealed  class Resource<T>(
    open var data: T? = null,
    open var message: String? = null

) {
    data class Success <T>(override var data: T?): Resource<T>()
    data class Failure<T>(override var message: String?): Resource<T>(message = message)
    data class Loading<T>(override var data: T?): Resource<T>()

    class Unspecified<T>(): Resource<T>()
}
