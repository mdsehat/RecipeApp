package com.example.recipe.data.repository

import com.example.recipe.data.source.LocalSource
import com.example.recipe.data.source.RemoteSource
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class RecipeRepository @Inject constructor(private val remoteRecipe: RemoteSource,
private val localSource: LocalSource) {
    val remote = remoteRecipe
    val local = localSource
}