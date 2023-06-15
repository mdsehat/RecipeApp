package com.example.recipe.data.source

import com.example.recipe.data.database.AppDao
import com.example.recipe.data.database.entity.DetailEntity
import com.example.recipe.data.database.entity.FavoriteEntity
import com.example.recipe.data.database.entity.RecipeEntity
import javax.inject.Inject

class LocalSource @Inject constructor(private val dao:AppDao) {

    //Recipe
    suspend fun saveRecipe(entity: RecipeEntity) = dao.saveRecipe(entity)
    fun loadRecipe() = dao.getRecipe()

    //Detail
    suspend fun saveDetail(entity: DetailEntity) = dao.saveDetail(entity)
    fun loadDetail(id:Int) = dao.getDetail(id)
    fun existsDetail(id:Int) = dao.existsDetail(id)

    //Favorite
    suspend fun saveFavorite(entity: FavoriteEntity) = dao.saveFavorite(entity)
    suspend fun deleteFavorite(entity: FavoriteEntity) = dao.deleteFavorite(entity)
    fun getAllFavorite() = dao.getAllFavorite()
    fun existsFavorite(id:Int) = dao.existsFavorite(id)


}