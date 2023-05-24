package com.example.recipe.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.recipe.data.model.recipe.ResponseRecipe
import com.example.recipe.utils.RECIPE_TABLE

@Entity(tableName = RECIPE_TABLE)
data class RecipeEntity (
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0,
    var response: ResponseRecipe
)