package com.training.skeleton.network

/**
 * A generic class that holds a value or an exception
 */
sealed class DataState<out R> {
    data class Success<out T>(val data: T) : DataState<T>()

    data class Error(val errorMessage: String) : DataState<Nothing>()

    data class Loading(val isLoading : Boolean) : DataState<Nothing>()
}