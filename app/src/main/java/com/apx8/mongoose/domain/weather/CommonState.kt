package com.apx8.mongoose.domain.weather

import com.apx8.mongoose.domain.util.Resource


sealed class CommonState<T> {

    class Loading<T>: CommonState<T>()

    data class Success<T>(val data: T): CommonState<T>()

    data class Error<T>(val message: String): CommonState<T>()

    fun isLoading(): Boolean = this is Loading

    fun isSuccessful(): Boolean = this is Success

    fun isFailed(): Boolean = this is Error

    companion object {

        /**
         * Returns [CommonState.Loading] instance.
         */
        fun <T> loading() = Loading<T>()

        /**
         * Returns [CommonState.Success] instance.
         * @param data Data to emit with status.
         */
        fun <T> success(data: T) = Success(data)

        /**
         * Returns [CommonState.Error] instance.
         * @param message Description of failure.
         */
        fun <T> error(message: String) = Error<T>(message)

        /**
         * Returns [CommonState] from [Resource]
         */
        fun <T> fromResource(resource: Resource<T>): CommonState<T> = when (resource) {
            is Resource.Success -> success(resource.data)
            is Resource.Failed -> error(resource.message)
        }
    }
}
