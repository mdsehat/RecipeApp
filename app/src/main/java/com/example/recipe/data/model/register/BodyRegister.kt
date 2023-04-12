package com.example.recipe.data.model.register


import com.google.gson.annotations.SerializedName

data class BodyRegister(
    @SerializedName("email")
    val email: String?, // your user's email
    @SerializedName("firstName")
    val firstName: String?, // your user's first name
    @SerializedName("lastName")
    val lastName: String?, // your user's last name
    @SerializedName("username")
    val username: String? // your user's name
)