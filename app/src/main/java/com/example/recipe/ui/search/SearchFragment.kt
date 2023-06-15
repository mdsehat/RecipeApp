package com.example.recipe.ui.search

import android.graphics.Rect
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipe.R
import com.example.recipe.adapter.RecentAdapter
import com.example.recipe.data.model.detail.DetailResponse
import com.example.recipe.data.model.recipe.ResponseRecipe
import com.example.recipe.databinding.FragmentLuckyBinding
import com.example.recipe.databinding.FragmentSearchBinding
import com.example.recipe.ui.recipe.RecipeFragmentDirections
import com.example.recipe.utils.NetworkChecker
import com.example.recipe.utils.NetworkResponse
import com.example.recipe.utils.setupRv
import com.example.recipe.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import javax.inject.Inject
import kotlin.properties.Delegates

@AndroidEntryPoint
class SearchFragment : Fragment() {

    //Binding
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var searchAdapter: RecentAdapter

    @Inject
    lateinit var networkChecker: NetworkChecker

    //Other
    private val viewModel: SearchViewModel by viewModels()
    private var isConnected by Delegates.notNull<Boolean>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //InitViews
        binding.apply {
            //Keyboard listener
            requireActivity().window.decorView.viewTreeObserver.addOnGlobalLayoutListener {
                if (isAdded){
                    val rect = Rect()
                    requireActivity().window.decorView.getWindowVisibleDisplayFrame(rect)
                    val height = requireActivity().window.decorView.height
                    if (height - rect.bottom <= height * 0.1399)
                        motionLay.transitionToStart()
                    else
                        motionLay.transitionToEnd()
                }

            }
            //Check connection
            lifecycleScope.launchWhenCreated {
                networkChecker.checkNetworkAvailability().collect{
                    isConnected = it
                    if (it){
                        motionLay.visibility = View.VISIBLE
                        connectionLaySearch.visibility = View.INVISIBLE

                    }else{
                        motionLay.visibility = View.INVISIBLE
                        connectionLaySearch.visibility = View.VISIBLE
                    }
                }
            }
        }
        //Call data
        callData()

        //Get data
        getDataSearch()
    }

    private fun callData(){
        binding.apply {
            searchEdt.addTextChangedListener {
                if (it.toString().length > 2 && isConnected)
                    viewModel.apply { callSearch(searchQueries(it.toString())) }
            }
        }
    }

    private fun getDataSearch() {
        viewModel.searchLiveData.observe(viewLifecycleOwner) { response ->
            binding.apply {
                when (response) {
                    is NetworkResponse.Loading -> {
                        rvSearch.showShimmer()
                    }
                    is NetworkResponse.Success -> {
                        rvSearch.hideShimmer()
                        //Get data
                        response.data?.let {
                            searchAdapter.setData(it.results!!)
                            setupSearchRecycler()
                        }
                    }
                    is NetworkResponse.Error -> {
                        rvSearch.hideShimmer()
                    }
                }
            }
        }
    }

    private fun setupSearchRecycler() {
            binding.rvSearch.setupRv(
                LinearLayoutManager(requireContext()),
                searchAdapter
            )
        //Set on click
        searchAdapter.setOnItemClick {
            val direction = SearchFragmentDirections.actionToDetail(it)
            findNavController().navigate(direction)
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}