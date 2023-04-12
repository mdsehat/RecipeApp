package com.example.recipe.utils

import retrofit2.Response

open class NetworkErrorCode<T>(private val response: Response<T>) {

    fun ErrorCode() : NetworkResponse<T>{
        return when{
            response.message().contains("timeout") -> NetworkResponse.Error("Timeout")
            response.code() == 401 -> NetworkResponse.Error("You are not authorized.")
            response.code() == 402 -> NetworkResponse.Error("Your free plan finished!")
            response.code() == 500 -> NetworkResponse.Error("Try again")
            response.isSuccessful -> NetworkResponse.Success(response.body()!!)
            else -> NetworkResponse.Error(response.message())
        }
    }

}