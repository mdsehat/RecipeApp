package com.example.recipe.utils

import androidx.recyclerview.widget.DiffUtil
import com.example.recipe.data.model.recipe.ResponseRecipe

class BaseDiffUtils<T>(private val newItem: List<T>, private val oldItem: List<T>) :
    DiffUtil.Callback() {
    override fun getOldListSize() = oldItem.size

    override fun getNewListSize() = newItem.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItem[oldItemPosition] === newItem[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItem[oldItemPosition] === newItem[newItemPosition]
    }
}