package com.example.recipe.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.request.CachePolicy
import com.example.recipe.R
import com.example.recipe.data.model.detail.DetailResponse.ExtendedIngredient
import com.example.recipe.data.model.detail.SimilarResponse.SimilarResponseItem
import com.example.recipe.databinding.ItemSimilarBinding
import com.example.recipe.utils.BASE_URL_IMAGE_INGREDIENT
import com.example.recipe.utils.BASE_URL_IMAGE_SIMILAR
import com.example.recipe.utils.BaseDiffUtils
import com.example.recipe.utils.NEW_SIZE_IMAGE
import javax.inject.Inject

class SimilarAdapter @Inject constructor():RecyclerView.Adapter<SimilarAdapter.Holder>() {
    //Binding
    private lateinit var binding: ItemSimilarBinding

    private var items = emptyList<SimilarResponseItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimilarAdapter.Holder {
        binding = ItemSimilarBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder()
    }

    override fun onBindViewHolder(holder: SimilarAdapter.Holder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int) = position

    override fun getItemId(position: Int) = position.toLong()

    inner class Holder():RecyclerView.ViewHolder(binding.root){
        @SuppressLint("SetTextI18n")
        fun bind(item:SimilarResponseItem){
            binding.apply {
                tvNameSimilar.text = item.title

                //Set image size
                val image = "${BASE_URL_IMAGE_SIMILAR}${item.id}-${NEW_SIZE_IMAGE}"
                //Load image
                imgSimilar.load(image){
                    crossfade(true)
                    crossfade(800)
                    memoryCachePolicy(CachePolicy.ENABLED)
                    error(R.drawable.ic_placeholder)
                }
                //ClickOnItem
                imgSimilar.setOnClickListener {
                    onClickItem?.let { it(item.id!!) }
                }
            }
        }
    }

    private var onClickItem : ((Int) -> Unit) ?= null

    fun setOnClickItem(listener: (Int)-> Unit){
        onClickItem = listener
    }

    fun setData(newData : List<SimilarResponseItem>){
        val diffUtil = BaseDiffUtils(newData, items)
        val calculateDiff = DiffUtil.calculateDiff(diffUtil)
        items = newData
        calculateDiff.dispatchUpdatesTo(this)
    }
}