package com.example.recipe.data.repository

import com.example.recipe.data.source.RemoteSource
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class RandomRepository @Inject constructor(private val remoteSource: RemoteSource) {
    val remote = remoteSource
}