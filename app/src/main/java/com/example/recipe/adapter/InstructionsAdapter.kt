package com.example.recipe.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.request.CachePolicy
import com.example.recipe.R
import com.example.recipe.data.model.detail.DetailResponse
import com.example.recipe.data.model.detail.DetailResponse.ExtendedIngredient
import com.example.recipe.data.model.recipe.ResponseRecipe.Result
import com.example.recipe.databinding.ItemInstructionBinding
import com.example.recipe.databinding.ItemPopularBinding
import com.example.recipe.utils.BASE_URL_IMAGE_INGREDIENT
import com.example.recipe.utils.BaseDiffUtils
import com.example.recipe.utils.NEW_SIZE_IMAGE
import com.example.recipe.utils.OLD_SIZE_IMAGE
import javax.inject.Inject

class InstructionsAdapter @Inject constructor():RecyclerView.Adapter<InstructionsAdapter.Holder>() {
    //Binding
    private lateinit var binding: ItemInstructionBinding

    private var items = emptyList<ExtendedIngredient>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InstructionsAdapter.Holder {
        binding = ItemInstructionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder()
    }

    override fun onBindViewHolder(holder: InstructionsAdapter.Holder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int) = position

    override fun getItemId(position: Int) = position.toLong()

    inner class Holder():RecyclerView.ViewHolder(binding.root){
        @SuppressLint("SetTextI18n")
        fun bind(item:ExtendedIngredient){
            binding.apply {
                tvNameInstruction.text = item.name
                tvCountInstruction.text = "${item.amount} ${item.unit}"

                //Set image size
                val image = "${BASE_URL_IMAGE_INGREDIENT}${item.image}"
                //Load image
                imgInstruction.load(image){
                    crossfade(true)
                    crossfade(800)
                    memoryCachePolicy(CachePolicy.ENABLED)
                    error(R.drawable.ic_placeholder)
                }
            }
        }
    }


    fun setData(newData : List<ExtendedIngredient>){
        val diffUtil = BaseDiffUtils(newData, items)
        val calculateDiff = DiffUtil.calculateDiff(diffUtil)
        items = newData
        calculateDiff.dispatchUpdatesTo(this)
    }
}