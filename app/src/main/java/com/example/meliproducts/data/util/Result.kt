package com.example.meliproducts.data.util

/**
 * Representa o resultado de uma operação que pode ter sucesso ou falha.
 */
sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Throwable) : Result<Nothing>()
}

