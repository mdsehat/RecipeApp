package com.example.recipe.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.request.CachePolicy
import com.example.recipe.R
import com.example.recipe.data.model.recipe.ResponseRecipe.Result
import com.example.recipe.databinding.ItemPopularBinding
import com.example.recipe.utils.BaseDiffUtils
import com.example.recipe.utils.NEW_SIZE_IMAGE
import com.example.recipe.utils.OLD_SIZE_IMAGE
import javax.inject.Inject

class PopularAdapter @Inject constructor():RecyclerView.Adapter<PopularAdapter.Holder>() {
    //Binding
    private lateinit var binding: ItemPopularBinding

    private var items = emptyList<Result>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularAdapter.Holder {
        binding = ItemPopularBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder()
    }

    override fun onBindViewHolder(holder: PopularAdapter.Holder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int) = position

    override fun getItemId(position: Int) = position.toLong()

    inner class Holder():RecyclerView.ViewHolder(binding.root){
        @SuppressLint("SetTextI18n")
        fun bind(item:Result){
            binding.apply {
                tvName.text = item.title.toString()
                tvPrice.text = "${item.pricePerServing} $"

                //Set image size
                val imageSplit = item.image!!.split("-")
                val imageSize = imageSplit[1].replace(OLD_SIZE_IMAGE, NEW_SIZE_IMAGE)
                //Load image
                imgPopular.load("${imageSplit[0]}-$imageSize"){
                    crossfade(true)
                    crossfade(800)
                    memoryCachePolicy(CachePolicy.ENABLED)
                    error(R.drawable.ic_placeholder)
                }
                //Click
                root.setOnClickListener {
                    onItemClick?.let { it(item.id!!) }
                }
            }
        }
    }

    private var onItemClick : ((Int) -> Unit) ?= null

    fun setOnItemClick(listener:(Int) -> Unit){
        onItemClick = listener
    }

    fun setData(newData : List<Result>){
        val diffUtil = BaseDiffUtils(newData, items)
        val calculateDiff = DiffUtil.calculateDiff(diffUtil)
        items = newData
        calculateDiff.dispatchUpdatesTo(this)
    }
}