package com.example.recipe.data.network

import com.example.recipe.data.model.detail.DetailResponse
import com.example.recipe.data.model.detail.SimilarResponse
import com.example.recipe.data.model.lucky.ResponseLucky
import com.example.recipe.data.model.recipe.ResponseRecipe
import com.example.recipe.data.model.register.BodyRegister
import com.example.recipe.data.model.register.ResponseRegister
import com.example.recipe.utils.API_KEY
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface ApiServices {

    @POST("users/connect")
    suspend fun postRegister(
        @Query(API_KEY) api: String,
        @Body body: BodyRegister
    ): Response<ResponseRegister>

    @GET("recipes/complexSearch")
    suspend fun getRecipe(@QueryMap queries: Map<String, String>): Response<ResponseRecipe>

    @GET("recipes/{id}/information")
    suspend fun getDetail(@Path("id") id:Int, @Query(API_KEY) apiKey: String) : Response<DetailResponse>

    @GET("recipes/{id}/similar")
    suspend fun getSimilar(@Path("id") id:Int, @Query(API_KEY) apiKey: String) : Response<SimilarResponse>

    @GET("recipes/random")
    suspend fun getRandom(@QueryMap queries: Map<String, String>) : Response<ResponseLucky>
}