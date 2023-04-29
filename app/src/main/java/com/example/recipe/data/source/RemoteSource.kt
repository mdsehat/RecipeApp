package com.example.recipe.data.source

import com.example.recipe.data.model.register.BodyRegister
import com.example.recipe.data.network.ApiServices
import javax.inject.Inject

class RemoteSource @Inject constructor(private val api: ApiServices) {

    suspend fun postRegister(apiKey: String, body: BodyRegister) = api.postRegister(apiKey,body)
    suspend fun getRecipe(queries: Map<String, String>) = api.getRecipe(queries)
}