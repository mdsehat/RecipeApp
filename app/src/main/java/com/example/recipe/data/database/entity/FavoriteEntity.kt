package com.example.recipe.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.recipe.data.model.detail.DetailResponse
import com.example.recipe.utils.DETAIL_TABLE
import com.example.recipe.utils.FAVORITE_TABLE

@Entity(tableName = FAVORITE_TABLE)
data class FavoriteEntity(
    @PrimaryKey(autoGenerate = false)
    val id : Int,
    val response: DetailResponse
)
