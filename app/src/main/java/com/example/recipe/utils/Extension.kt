package com.example.recipe.utils

import android.content.res.ColorStateList
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
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

fun <T> LiveData<T>.singleObserve(owner: LifecycleOwner, observe: Observer<T>){
    observe(owner, object : Observer<T>{
        override fun onChanged(t: T) {
            removeObserver(this)
            observe.onChanged(t)
        }

    })
}

fun hourToMin(time: Int) : String{
    var hour = 0
    var minute = 0

    if (time > 60){
        hour = time/60
        minute = time % 60
        return "${hour}h ${minute}min"
    }else{
        return "${time}min"
    }
}

fun ImageView.setTint(color:Int){
    imageTintList = ColorStateList.valueOf(ContextCompat.getColor(context, color))
}