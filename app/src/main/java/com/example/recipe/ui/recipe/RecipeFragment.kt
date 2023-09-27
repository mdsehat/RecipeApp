package com.example.recipe.ui.recipe

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.example.recipe.R
import com.example.recipe.adapter.PopularAdapter
import com.example.recipe.adapter.RecentAdapter
import com.example.recipe.data.model.recipe.ResponseRecipe
import com.example.recipe.databinding.FragmentRecipeBinding
import com.example.recipe.ui.menu.MenuFragmentDirections
import com.example.recipe.utils.*
import com.example.recipe.viewmodel.RecipeViewModel
import com.example.recipe.viewmodel.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import javax.inject.Inject

@AndroidEntryPoint
class RecipeFragment : Fragment() {

    //Binding
    private var _binding: FragmentRecipeBinding? = null
    private val binding get() = _binding!!

    private val TAG = "tagRe"
    @Inject
    lateinit var popularAdapter: PopularAdapter

    @Inject
    lateinit var recentAdapter: RecentAdapter

    @Inject
    lateinit var networkChecker: NetworkChecker

    //Other
    private val viewModel: RecipeViewModel by viewModels()
    private val registerViewModel: RegisterViewModel by viewModels()
    private var indexAutoScroll = 0
    private val args: RecipeFragmentArgs by navArgs()

    //Snap
    private val snapHelper = LinearSnapHelper()

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
            lifecycleScope.launchWhenCreated {

                registerViewModel.readData().collect {
                    userName.text = "${getString(R.string.hello)} ,${it.username} ${showUnicode()}"
                }
            }
            //Call data
            callPopular()
            callRecent()
            //Load data
            loadCallPopular()
            loadCallRecent()
        }
    }

    //--popular--//
    private fun callPopular() {
        setPopularRecycler()
        viewModel.readPopularFromDb.singleObserve(viewLifecycleOwner) { database ->
            if (database.isNotEmpty()) {
                database[0].response.results?.let { data ->
                    fillAdapterPopular(data)
                }
            } else {
                viewModel.apply { callPopular(popularQueries()) }
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
        //Set on click
        popularAdapter.setOnItemClick {
            val direction = RecipeFragmentDirections.actionToDetail(it)
            findNavController().navigate(direction)
        }
    }

    private fun setAnimForRv(list: List<ResponseRecipe.Result>) {
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

    //--Recent--//
    private fun callRecent() {
        Log.i(TAG, "callRecent: " + args.isUpdated)
        setRecentRecycler()
        viewModel.readRecentFromDb.singleObserve(viewLifecycleOwner) { database ->
            if (database.isNotEmpty() && database.size > 1 && !args.isUpdated) {
                database[1].response.results?.let { data ->
                    recentAdapter.setData(data)
                }
            } else {
                viewModel.apply { callRecent(recentQueries()) }
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

                            setRecentRecycler()
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

    private fun setRecentRecycler() {
        //InitRv
        binding.rvRecent.setupRv(LinearLayoutManager(requireContext()), recentAdapter)
        //Set on click
        recentAdapter.setOnItemClick {
            val direction = RecipeFragmentDirections.actionToDetail(it)
            findNavController().navigate(direction)
        }
    }

    //--Other--//
    private fun showUnicode(): String {
        return String(Character.toChars(0x1f44b))
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}