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
import com.example.recipe.data.model.detail.DetailResponse.AnalyzedInstruction.Step
import com.example.recipe.data.model.detail.DetailResponse.ExtendedIngredient
import com.example.recipe.data.model.recipe.ResponseRecipe.Result
import com.example.recipe.databinding.ItemInstructionBinding
import com.example.recipe.databinding.ItemPopularBinding
import com.example.recipe.databinding.ItemStepBinding
import com.example.recipe.utils.*
import javax.inject.Inject

class StepsAdapter @Inject constructor():RecyclerView.Adapter<StepsAdapter.Holder>() {
    //Binding
    private lateinit var binding: ItemStepBinding

    private var items = emptyList<Step>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StepsAdapter.Holder {
        binding = ItemStepBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder()
    }

    override fun onBindViewHolder(holder: StepsAdapter.Holder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = COUNT_STEPS

    override fun getItemViewType(position: Int) = position

    override fun getItemId(position: Int) = position.toLong()

    inner class Holder():RecyclerView.ViewHolder(binding.root){
        @SuppressLint("SetTextI18n")
        fun bind(item:Step){
            binding.apply {
                tvNumberStep.text = "${adapterPosition + 1}"
                tvDescStep.text = item.step
                item.length?.let {
                    tvTimeStep.text = hourToMin(item.length.number!!)
                }
            }
        }
    }


    fun setData(newData : List<Step>){
        val diffUtil = BaseDiffUtils(newData, items)
        val calculateDiff = DiffUtil.calculateDiff(diffUtil)
        items = newData
        calculateDiff.dispatchUpdatesTo(this)
    }
}