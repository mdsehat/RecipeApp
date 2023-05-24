package com.example.recipe.ui.recipe

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.GeneratedAdapter
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.example.recipe.R
import com.example.recipe.adapter.PopularAdapter
import com.example.recipe.adapter.RecentAdapter
import com.example.recipe.data.model.recipe.ResponseRecipe
import com.example.recipe.databinding.FragmentRecipeBinding
import com.example.recipe.databinding.FragmentSplashBinding
import com.example.recipe.utils.*
import com.example.recipe.viewmodel.RecipeViewModel
import com.example.recipe.viewmodel.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@AndroidEntryPoint
class RecipeFragment : Fragment() {

    //Binding
    private var _binding: FragmentRecipeBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var popularAdapter: PopularAdapter

    @Inject
    lateinit var recentAdapter: RecentAdapter

    @Inject
    lateinit var networkChecker: NetworkChecker

    //Other
    private val viewModel: RecipeViewModel by viewModels()
    private var indexAutoScroll = 0


    private val registerViewModel: RegisterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeBinding.inflate(layoutInflater)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //InitViews
        binding.apply {
            //Show username
            lifecycleScope.launchWhenStarted {
                registerViewModel.readData().collect {
                    userName.text = "${getString(R.string.hello)} ,${it.username} ${showUnicode()}"
                }
            }
            callData()
            loadCallPopular()
            loadCallRecent()
        }
    }

    private fun callData() {
        //Init rv
        setPopularRecycler()
        setRecentAdapter()
        //CallData
        lifecycleScope.launchWhenCreated {
            networkChecker.checkNetworkAvailability().collect { online ->
                if (online) {
                    //API
                    viewModel.apply { callPopular(popularQueries()) }
                    viewModel.apply { callRecent(recentQueries()) }
                } else {
                    //Database
                    viewModel.readPopularFromDb.observe(viewLifecycleOwner) { database ->
                        if (database.isNotEmpty()) {
                            database[0].response.results?.let { data ->
                                fillAdapterPopular(data)
                            }
                        }
                    }
                    viewModel.readRecentFromDb.observe(viewLifecycleOwner) { database ->
                        if (database.isNotEmpty() && database.size > 1) {
                            database[1].response.results?.let { data ->
                                recentAdapter.setData(data)
                            }
                        }
                    }
                }
            }
        }

    }

    private fun loadCallPopular() {
        binding.apply {
            viewModel.popularLiveData.observe(viewLifecycleOwner) { response ->
                when (response) {
                    is NetworkResponse.Loading -> {
                        rvPopular.showShimmer()
                    }
                    is NetworkResponse.Success -> {
                        rvPopular.hideShimmer()
                        response.data?.results?.let { data ->
                            fillAdapterPopular(data)
                        }
                    }
                    is NetworkResponse.Error -> {
                        rvPopular.hideShimmer()
                        root.makeSnackBar(response.message!!)
                    }
                }
            }
        }
    }

    private fun fillAdapterPopular(data: List<ResponseRecipe.Result>) {
        popularAdapter.setData(data)
        setAnimForRv(data)
    }

    private fun setPopularRecycler() {
        //InitRv
        binding.rvPopular
            .setupRv(
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false),
                popularAdapter
            )
    }

    private fun setAnimForRv(list: List<ResponseRecipe.Result>) {
        //Snap
        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(binding.rvPopular)
        //AutoScroll
        lifecycleScope.launchWhenStarted {
            repeat(REPEAT_TIME) {
                delay(DELAY_TIME)
                if (indexAutoScroll < list.size) indexAutoScroll++
                else indexAutoScroll = 0
                binding.rvPopular.smoothScrollToPosition(indexAutoScroll)
            }

        }
    }

    private fun loadCallRecent() {
        viewModel.recentLiveData.observe(viewLifecycleOwner) { response ->
            binding.apply {
                when (response) {
                    is NetworkResponse.Loading -> {
                        rvRecent.showShimmer()
                    }
                    is NetworkResponse.Success -> {
                        rvRecent.hideShimmer()
                        response.data?.let {
                            recentAdapter.setData(it.results!!)
                            setRecentAdapter()
                        }
                    }
                    is NetworkResponse.Error -> {
                        rvRecent.showShimmer()
                        root.makeSnackBar(response.message!!)
                    }
                }
            }

        }
    }

    private fun setRecentAdapter() {
        binding.rvRecent.setupRv(LinearLayoutManager(requireContext()), recentAdapter)
    }

    private fun showUnicode(): String {
        return String(Character.toChars(0x1f44b))
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}