package com.example.recipe.data.source

import com.example.recipe.data.database.AppDao
import com.example.recipe.data.database.entity.DetailEntity
import com.example.recipe.data.database.entity.RecipeEntity
import javax.inject.Inject

class LocalSource @Inject constructor(private val dao:AppDao) {
    suspend fun saveRecipe(entity: RecipeEntity) = dao.saveRecipe(entity)
    fun loadRecipe() = dao.getRecipe()
    suspend fun saveDetail(entity: DetailEntity) = dao.saveDetail(entity)
    fun loadDetail(id:Int) = dao.getDetail(id)
    fun existsDetail(id:Int) = dao.existsDetail(id)
}