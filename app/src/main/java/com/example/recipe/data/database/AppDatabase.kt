package com.example.recipe.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [RecipeEntity::class], version = 1, exportSchema = false)
@TypeConverters(AppTypeConverter::class)
abstract class AppDatabase : RoomDatabase(){
    abstract fun dao(): AppDao
}