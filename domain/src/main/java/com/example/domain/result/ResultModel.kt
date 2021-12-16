package com.example.domain.result

sealed class ResultModel<T> {
    class Idle<T> : ResultModel<T>()
    class Loading<T> : ResultModel<T>()
    data class Success<T>(val data: T) : ResultModel<T>()
    data class Error<T>(val errMessage: String?) : ResultModel<T>()
}
