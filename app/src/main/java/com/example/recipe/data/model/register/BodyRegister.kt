package com.example.recipe.data.model.register


import com.google.gson.annotations.SerializedName

data class BodyRegister(
    @SerializedName("email")
    val email: String?= null, // your user's email
    @SerializedName("firstName")
    val firstName: String?= null, // your user's first name
    @SerializedName("lastName")
    val lastName: String?= null, // your user's last name
    @SerializedName("username")
    val username: String?= null // your user's name
)