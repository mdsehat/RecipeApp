package com.example.recipe.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.recipe.data.database.entity.DetailEntity
import com.example.recipe.data.database.entity.FavoriteEntity
import com.example.recipe.data.database.entity.RecipeEntity

@Database(entities = [RecipeEntity::class, DetailEntity::class, FavoriteEntity::class], version = 4, exportSchema = false)
@TypeConverters(AppTypeConverter::class)
abstract class AppDatabase : RoomDatabase(){
    abstract fun dao(): AppDao
}