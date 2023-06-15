package com.example.recipe.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.example.recipe.data.database.entity.DetailEntity
import com.example.recipe.data.database.entity.FavoriteEntity
import com.example.recipe.data.database.entity.RecipeEntity
import com.example.recipe.utils.DETAIL_TABLE
import com.example.recipe.utils.FAVORITE_TABLE
import com.example.recipe.utils.RECIPE_TABLE
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {
    //--Recipe--//
    @Insert(onConflict = REPLACE)
    suspend fun saveRecipe(entity: RecipeEntity)

    @Query("SELECT * FROM ${RECIPE_TABLE}")
    fun getRecipe(): Flow<List<RecipeEntity>>

    //--Detail--//
    @Insert(onConflict = REPLACE)
    suspend fun saveDetail(entity: DetailEntity)

    @Query("SELECT * FROM ${DETAIL_TABLE} WHERE id =:id")
    fun getDetail(id:Int):Flow<DetailEntity>

    @Query("SELECT EXISTS (SELECT 1 FROM ${DETAIL_TABLE} WHERE id =:id)")
    fun existsDetail(id:Int):Flow<Boolean>

    //Favorite
    @Insert(onConflict = REPLACE)
    suspend fun saveFavorite(entity: FavoriteEntity)

    @Query("SELECT * FROM ${FAVORITE_TABLE}")
    fun getAllFavorite():Flow<List<FavoriteEntity>>

    @Query("SELECT EXISTS (SELECT 1 FROM ${FAVORITE_TABLE} WHERE id =:id)")
    fun existsFavorite(id:Int):Flow<Boolean>

    @Delete
    suspend fun deleteFavorite(entity: FavoriteEntity)

}