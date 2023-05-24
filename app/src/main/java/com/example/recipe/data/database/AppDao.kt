package com.example.recipe.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.example.recipe.data.model.recipe.ResponseRecipe
import com.example.recipe.utils.RECIPE_TABLE
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {
    //--Recipe--//
    @Insert(onConflict = REPLACE)
    suspend fun saveRecipe(entity: RecipeEntity)

    @Query("SELECT * FROM ${RECIPE_TABLE}")
    fun getRecipe(): Flow<List<RecipeEntity>>

}