package com.example.recipe.data.model.detail


import com.google.gson.annotations.SerializedName

class SimilarResponse : ArrayList<SimilarResponse.SimilarResponseItem>(){
    data class SimilarResponseItem(
        @SerializedName("id")
        val id: Int?, // 422994
        @SerializedName("imageType")
        val imageType: String?, // jpg
        @SerializedName("readyInMinutes")
        val readyInMinutes: Int?, // 30
        @SerializedName("servings")
        val servings: Int?, // 5
        @SerializedName("sourceUrl")
        val sourceUrl: String?, // http://www.tasteofhome.com/Recipes/cannellini-bean-salad-2
        @SerializedName("title")
        val title: String? // Cannellini Bean Salad
    )
}