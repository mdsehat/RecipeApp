package com.example.recipe.ui.lucky

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import coil.request.CachePolicy
import com.example.recipe.R
import com.example.recipe.adapter.InstructionsAdapter
import com.example.recipe.adapter.SimilarAdapter
import com.example.recipe.adapter.StepsAdapter
import com.example.recipe.data.model.detail.DetailResponse
import com.example.recipe.data.model.lucky.ResponseLucky
import com.example.recipe.databinding.FragmentFavoriteBinding
import com.example.recipe.databinding.FragmentLuckyBinding
import com.example.recipe.ui.detail.DetailFragmentDirections
import com.example.recipe.utils.*
import com.example.recipe.viewmodel.RandomViewModel
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import com.google.android.material.chip.ChipGroup
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import javax.inject.Inject

@AndroidEntryPoint
class LuckyFragment : Fragment() {

    //Binding
    private var _binding: FragmentLuckyBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var instructionsAdapter: InstructionsAdapter

    @Inject
    lateinit var stepsAdapter: StepsAdapter

    @Inject
    lateinit var networkChecker: NetworkChecker

    //Other
    private val viewModel: RandomViewModel by viewModels()
    private var counterChip = 1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentLuckyBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Call api
        lifecycleScope.launchWhenStarted {
            networkChecker.checkNetworkAvailability().collect { isConnected ->
                if (isConnected){
                    viewModel.apply { callLucky(luckyQueries()) }
                }else{
                    visibilityView(content = false, loading = false, connection = true)
                }
            }
        }
        //Load data
        getDataLucky()
    }
    private fun getDataLucky() {
        viewModel.luckyLiveData.observe(viewLifecycleOwner) { response ->
            binding.apply {
                when (response) {
                    is NetworkResponse.Loading -> {
                        visibilityView(content = false, loading = true, connection = false)
                    }
                    is NetworkResponse.Success -> {
                        //Get data
                        response.data?.let { data ->
                            initViewsByData(data.recipes!![0])
                        }
                        visibilityView(content = true, loading = false, connection = false)
                    }
                    is NetworkResponse.Error -> {
                        visibilityView(content = true, loading = false, connection = false)
                    }
                }
            }
        }
    }
    private fun initViewsByData(data: ResponseLucky.Recipe) {
        binding.apply {
            //Cover
            val imageSplit = data.image!!.split("-")
            val imageNewSize = imageSplit[1].replace(OLD_SIZE_IMAGE, NEW_SIZE_IMAGE)
            coverImg.load("${imageSplit[0]}-${imageNewSize}") {
                crossfade(true)
                crossfade(800)
                memoryCachePolicy(CachePolicy.ENABLED)
                error(R.drawable.ic_placeholder)
            }
            //Source
            data.sourceUrl?.let { link ->
                iconSource.visibility = View.VISIBLE
                iconSource.setOnClickListener {
                    val direction = DetailFragmentDirections.actionToWeb(link)
                    findNavController().navigate(direction)
                }
            }
            //Name
            tvName.text = data.title
            //Time
            tvTime.text = hourToMin(data.readyInMinutes!!)
            //Desc
            val summary = HtmlCompat.fromHtml(data.summary!!, HtmlCompat.FROM_HTML_MODE_COMPACT)
            desc.text = summary
            //Toggle
            if (data.cheap!!) iconCheap.setCustomColor(R.color.caribbean_green)
            if (data.dairyFree!!) iconDairy.setCustomColor(R.color.caribbean_green)
            if (data.vegan!!) iconVegan.setCustomColor(R.color.caribbean_green)
            if (data.veryPopular!!) iconPopular.setCustomColor(R.color.caribbean_green)
            //Like
            iconLike.text = data.aggregateLikes.toString()
            //Price
            iconPrice.text = "${data.pricePerServing.toString()} $"
            //Healthy
            iconHealthy.text = data.healthScore.toString()
            when (data.healthScore!!) {
                in 0..54 -> iconHealthy.setCustomColor(R.color.tart_orange)
                in 55..89 -> iconHealthy.setCustomColor(R.color.chineseYellow)
                in 90..100 -> iconHealthy.setCustomColor(R.color.caribbean_green)
            }
            //Instructions
            instructionCount.text = "${data.extendedIngredients!!.size} Items"
            val summaryInstruction =
                HtmlCompat.fromHtml(data.instructions!!, HtmlCompat.FROM_HTML_MODE_COMPACT)
            instructionDesc.text = summaryInstruction
            setupInstructionsRecycler(data.extendedIngredients)
            //Steps
            setupStepsRecycler(data.analyzedInstructions!![0].steps!!)
            //Show more
            binding.btnShowMore.setOnClickListener {
                val directions =
                    DetailFragmentDirections.actionDetailToSteps(data.analyzedInstructions[0])
                findNavController().navigate(directions)
            }
            //Diets chip
            setupChip(data.diets!!, dietGroup)
        }
    }

    private fun setupInstructionsRecycler(list: List<DetailResponse.ExtendedIngredient>) {
        if (list.isNotEmpty()) {
            instructionsAdapter.setData(list)
            binding.instructionList.setupRv(
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false),
                instructionsAdapter
            )
        }
    }

    private fun setupStepsRecycler(list: List<DetailResponse.AnalyzedInstruction.Step>) {
        if (list.isNotEmpty()) {
            //Count items
            COUNT_STEPS = if (list.size < 3) {
                list.size
            } else {
                3
            }
            //Init adapter
            stepsAdapter.setData(list)
            binding.stepsList.setupRv(
                LinearLayoutManager(requireContext()), stepsAdapter
            )
            //Btn show more
            if (list.size > 3) binding.btnShowMore.visibility = View.VISIBLE

        }
    }

    private fun setupChip(diets: List<String>, dietGroup: ChipGroup) {
        diets.forEach {
            val chip = Chip(requireContext())
            val drawable =
                ChipDrawable.createFromAttributes(requireContext(), null, 0, R.style.dietChip)
            chip.setChipDrawable(drawable)
            chip.text = it
            chip.id = counterChip++
            dietGroup.addView(chip)
        }
    }

    private fun visibilityView(content: Boolean, loading: Boolean, connection: Boolean) {
        if (content) binding.contentLay.visibility = View.VISIBLE
        else binding.contentLay.visibility = View.INVISIBLE

        if (loading) binding.detailProgress.visibility = View.VISIBLE
        else binding.detailProgress.visibility = View.INVISIBLE

        if (connection) binding.connectionLay.visibility = View.VISIBLE
        else binding.connectionLay.visibility = View.INVISIBLE
    }



    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}