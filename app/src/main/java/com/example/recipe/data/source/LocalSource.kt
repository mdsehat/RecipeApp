package com.example.recipe.data.source

import com.example.recipe.data.database.AppDao
import com.example.recipe.data.database.RecipeEntity
import javax.inject.Inject

class LocalSource @Inject constructor(private val dao:AppDao) {
    suspend fun saveItem(entity: RecipeEntity) = dao.saveRecipe(entity)
    fun loadItems() = dao.getRecipe()
}