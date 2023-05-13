package com.example.recipe.utils

import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

fun View.makeSnackBar(message: String){
    Snackbar.make(this, message, Snackbar.LENGTH_SHORT).show()
}

fun RecyclerView.setupRv(layout: RecyclerView.LayoutManager, myAdapter: RecyclerView.Adapter<*>){
    this.apply {
        layoutManager = layout
        setHasFixedSize(true)
        adapter = myAdapter
    }
}

fun TextView.setCustomColor(color: Int){
    this.setTextColor(ContextCompat.getColor(context,color))
    this.compoundDrawables[1].setTint(ContextCompat.getColor(context,color))
}