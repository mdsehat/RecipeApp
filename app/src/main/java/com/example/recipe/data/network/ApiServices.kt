package com.example.recipe.data.network

import com.example.recipe.data.model.register.BodyRegister
import com.example.recipe.data.model.register.ResponseRegister
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiServices {

    @POST("users/connect")
    suspend fun postRegister(@Query("apiKey")api :String, @Body body: BodyRegister) : Response<ResponseRegister>
}