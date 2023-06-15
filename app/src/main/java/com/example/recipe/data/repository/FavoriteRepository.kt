package com.example.recipe.data.repository

import com.example.recipe.data.source.LocalSource
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class FavoriteRepository @Inject constructor(private val localSource: LocalSource){
    val local = localSource
}