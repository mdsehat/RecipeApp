package com.example.recipe.data.database

import androidx.room.TypeConverter
import com.example.recipe.data.model.detail.DetailResponse
import com.example.recipe.data.model.recipe.ResponseRecipe
import com.google.gson.Gson

class AppTypeConverter {

    private val gson = Gson()

    @TypeConverter
    fun recipeToJson(model: ResponseRecipe):String{
        return gson.toJson(model)
    }

    @TypeConverter
    fun stringToRecipe(data: String): ResponseRecipe{
        return gson.fromJson(data,ResponseRecipe::class.java)
    }

    @TypeConverter
    fun stringToDetail(data: String): DetailResponse{
        return gson.fromJson(data,DetailResponse::class.java)
    }

    @TypeConverter
    fun detailToJson(model: DetailResponse):String{
        return gson.toJson(model)
    }


}