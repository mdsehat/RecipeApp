package com.example.recipe.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.request.CachePolicy
import com.example.recipe.R
import com.example.recipe.data.model.recipe.ResponseRecipe.Result
import com.example.recipe.databinding.ItemPopularBinding
import com.example.recipe.databinding.ItemRecentBinding
import com.example.recipe.utils.*
import javax.inject.Inject

class RecentAdapter @Inject constructor():RecyclerView.Adapter<RecentAdapter.Holder>() {
    //Binding
    private lateinit var binding: ItemRecentBinding

    private var items = emptyList<Result>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentAdapter.Holder {
        binding = ItemRecentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder()
    }

    override fun onBindViewHolder(holder: RecentAdapter.Holder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int) = position

    override fun getItemId(position: Int) = position.toLong()

    inner class Holder():RecyclerView.ViewHolder(binding.root){
        @SuppressLint("SetTextI18n")
        fun bind(item:Result){
            binding.apply {
                //FillItem
                nameRecent.text = item.title
                val summary = HtmlCompat.fromHtml(item.summary!!, HtmlCompat.FROM_HTML_MODE_COMPACT)
                descRecent.text = summary
                likeRecent.text = item.aggregateLikes.toString()
                //time
                timeRecent.text = hourToMin(item.readyInMinutes!!)
                //Vegan
                if (item.vegan!!){
                    veganRecent.setCustomColor(R.color.caribbean_green)
                }else{
                    veganRecent.setCustomColor(R.color.gray)
                }
                //Healthy
                healthRecent.text = item.healthScore.toString()
                when(item.healthScore!!){
                    in 0..54 -> healthRecent.setCustomColor(R.color.tart_orange)
                    in 55..89 -> healthRecent.setCustomColor(R.color.chineseYellow)
                    in 90..100 -> healthRecent.setCustomColor(R.color.caribbean_green)
                }
                //Set image size
                val imageSplit = item.image!!.split("-")
                val imageSize = imageSplit[1].replace(OLD_SIZE_IMAGE, NEW_SIZE_IMAGE)
                //Load image
                imgRecent.load("${imageSplit[0]}-$imageSize"){
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