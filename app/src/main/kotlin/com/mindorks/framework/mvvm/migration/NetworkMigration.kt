package com.mindorks.framework.mvvm.migration

import com.mindorks.framework.mvvm.data.network.NetworkResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

/**
 * Migration utilities for converting RxJava to Coroutines
 */
object NetworkMigration {

    /**
     * Convert Retrofit Response to NetworkResult Flow
     */
    fun <T> Response<T>.toNetworkResult(): NetworkResult<T> {
        return if (isSuccessful) {
            body()?.let { body ->
                NetworkResult.Success(body)
            } ?: NetworkResult.Error("Empty response body")
        } else {
            NetworkResult.Error(
                message = message() ?: "Unknown error",
                code = code()
            )
        }
    }

    /**
     * Convert suspend function to Flow with loading states
     */
    fun <T> flowWithLoading(
        apiCall: suspend () -> Response<T>
    ): Flow<NetworkResult<T>> = flow {
        emit(NetworkResult.Loading(true))
        try {
            val response = apiCall()
            emit(response.toNetworkResult())
        } catch (e: Exception) {
            emit(NetworkResult.Error(e.message ?: "Network error occurred"))
        } finally {
            emit(NetworkResult.Loading(false))
        }
    }

    /**
     * Safe API call wrapper
     */
    suspend fun <T> safeApiCall(
        apiCall: suspend () -> Response<T>
    ): NetworkResult<T> {
        return try {
            val response = apiCall()
            response.toNetworkResult()
        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: "Network error occurred")
        }
    }
}