package com.example.recipe.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.recipe.data.model.detail.DetailResponse
import com.example.recipe.utils.DETAIL_TABLE

@Entity(tableName = DETAIL_TABLE)
data class DetailEntity(
    @PrimaryKey(autoGenerate = false)
    val id : Int,
    val response: DetailResponse
)
