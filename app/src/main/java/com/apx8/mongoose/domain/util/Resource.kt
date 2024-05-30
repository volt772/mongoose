package com.apx8.mongoose.domain.util

sealed class Resource2<T> {

    class Success<T>(val data: T): Resource2<T>()

    class Failed<T>(val message: String): Resource2<T>()

}