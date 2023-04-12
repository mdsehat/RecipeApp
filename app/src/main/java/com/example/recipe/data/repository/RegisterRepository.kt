package com.example.recipe.data.repository

import com.example.recipe.data.model.register.BodyRegister
import com.example.recipe.data.source.RemoteSource
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class RegisterRepository @Inject constructor(private val remote: RemoteSource) {

    suspend fun postRegister(apiKey: String, body: BodyRegister) = remote.postRegister(apiKey,body)

}