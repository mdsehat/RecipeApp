package com.example.recipe.ui.detail

import android.annotation.SuppressLint
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
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import coil.request.CachePolicy
import com.example.recipe.R
import com.example.recipe.adapter.InstructionsAdapter
import com.example.recipe.adapter.SimilarAdapter
import com.example.recipe.adapter.StepsAdapter
import com.example.recipe.data.database.entity.FavoriteEntity
import com.example.recipe.data.model.detail.DetailResponse
import com.example.recipe.data.model.detail.DetailResponse.AnalyzedInstruction.Step
import com.example.recipe.data.model.detail.DetailResponse.ExtendedIngredient
import com.example.recipe.data.model.detail.SimilarResponse
import com.example.recipe.databinding.FragmentDetailBinding
import com.example.recipe.utils.*
import com.example.recipe.viewmodel.DetailViewModel
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import com.google.android.material.chip.ChipGroup
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import javax.inject.Inject

@AndroidEntryPoint
class DetailFragment : Fragment() {
    //Binding
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var instructionsAdapter: InstructionsAdapter

    @Inject
    lateinit var stepsAdapter: StepsAdapter

    @Inject
    lateinit var similarAdapter: SimilarAdapter

    @Inject
    lateinit var networkChecker: NetworkChecker

    //Other
    private val viewModel: DetailViewModel by viewModels()
    private val args: DetailFragmentArgs by navArgs()
    private var recipeId = 0
    private var counterChip = 1
    private var isExistsCache = false
    private var isExistsFavorite = false
    private val TAG = "tagCache"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Args
        args.let { recipeId = it.recipeID }
        //InitViews
        binding.apply {
            //Back
            iconBack.setOnClickListener {
                findNavController().popBackStack(R.id.detailFragment, true)
            }
        }
        //Call data
        lifecycleScope.launchWhenStarted {
            networkChecker.checkNetworkAvailability().collect { isConnected ->
                //Checking cache
                existsCache(recipeId)
                delay(200)
                //Load
                if (isExistsCache) {
                    loadDetailFromDb(recipeId)
                } else {
                    if (isConnected) {
                        getDataDetail()
                    } else {
                        visibilityView(content = false, loading = false, connection = true)
                    }
                }
                if (isConnected) getDataSimilar()
            }
        }

    }

    //--Load data--//
    private fun getDataDetail() {
        viewModel.callDetail(recipeId, API_KEY_NUMBER)
        viewModel.detailLiveData.observe(viewLifecycleOwner) { response ->
            binding.apply {
                when (response) {
                    is NetworkResponse.Loading -> {
                        visibilityView(content = false, loading = true, connection = false)
                    }
                    is NetworkResponse.Success -> {
                        //Get data
                        response.data?.let { data ->
                            initViewsByData(data)
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

    private fun getDataSimilar() {
        viewModel.callSimilar(recipeId, API_KEY_NUMBER)
        viewModel.similarLiveData.observe(viewLifecycleOwner) { response ->
            binding.apply {
                when (response) {
                    is NetworkResponse.Loading -> {
                        similarList.showShimmer()
                    }
                    is NetworkResponse.Success -> {
                        similarList.hideShimmer()
                        //Get data
                        response.data?.let { data ->
                            initSimilarRecycler(data)
                        }

                    }
                    is NetworkResponse.Error -> {
                        similarList.hideShimmer()
                    }
                }
            }
        }
    }

    private fun loadDetailFromDb(id: Int) {
        viewModel.loadDetailFromDb(id).observe(viewLifecycleOwner) {
            initViewsByData(it.response)
            visibilityView(content = true, loading = false, connection = false)
        }

    }

    //--Init views--//
    private fun initViewsByData(data: DetailResponse) {
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
            //Favorite
            viewModel.existsFavorite(recipeId)
            checkExistsFavorite()
            iconFav.setOnClickListener {
                if (isExistsFavorite){
                    deleteFavorite(data)
                }else{
                    saveFavorite(data)
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

    private fun initSimilarRecycler(data: List<SimilarResponse.SimilarResponseItem>) {
        similarAdapter.setData(data)
        binding.similarList.setupRv(
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false),
            similarAdapter
        )
        //OnClick
        similarAdapter.setOnClickItem {
            val direction = DetailFragmentDirections.actionToDetail(it)
            findNavController().navigate(direction)
        }
    }

    private fun setupInstructionsRecycler(list: List<ExtendedIngredient>) {
        if (list.isNotEmpty()) {
            instructionsAdapter.setData(list)
            binding.instructionList.setupRv(
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false),
                instructionsAdapter
            )
        }
    }

    private fun setupStepsRecycler(list: List<Step>) {
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

    //--Favorite--//
    private fun saveFavorite(data: DetailResponse){
        val entity = FavoriteEntity(data.id!!, data)
        viewModel.saveFavorite(entity)
        //Change icon
        binding.iconFav.apply {
            setTint(R.color.tart_orange)
            setImageResource(R.drawable.ic_heart_circle_minus)
        }
    }
    private fun deleteFavorite(data: DetailResponse){
        val entity = FavoriteEntity(data.id!!, data)
        viewModel.deleteFavorite(entity)
        //Change icon
        binding.iconFav.apply {
            setTint(R.color.persianGreen)
            setImageResource(R.drawable.ic_heart_circle_plus)
        }
    }

    private fun checkExistsFavorite(){
        viewModel.existsFavoriteLiveData.observe(viewLifecycleOwner){
            isExistsFavorite = it
            if (it){
                binding.iconFav.apply {
                    setTint(R.color.tart_orange)
                    setImageResource(R.drawable.ic_heart_circle_minus)
                }
            }else{
                binding.iconFav.apply {
                    setTint(R.color.persianGreen)
                    setImageResource(R.drawable.ic_heart_circle_plus)
                }
            }
        }
    }

    //--Other--//
    private fun existsCache(id: Int) {
        viewModel.existsDetail(id)
        //Load
        viewModel.existsDetailLiveData.observe(viewLifecycleOwner) {
            isExistsCache = it
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