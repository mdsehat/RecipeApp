package com.example.recipe.utils

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.makeSnackBar(message: String){
    Snackbar.make(this, message, Snackbar.LENGTH_SHORT).show()
}