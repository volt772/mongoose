package com.apx8.mongoose.domain.util

sealed class Resource<T> {
    class Success<T>(val data: T): Resource<T>()
    class Failed<T>(val message: String): Resource<T>()
}