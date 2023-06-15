package com.example.recipe.ui.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipe.R
import com.example.recipe.adapter.FavoriteAdapter
import com.example.recipe.databinding.FragmentFavoriteBinding
import com.example.recipe.databinding.FragmentMenuBinding
import com.example.recipe.utils.setupRv
import com.example.recipe.viewmodel.FavoriteViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FavoriteFragment : Fragment() {

    //Binding
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var favoriteAdapter: FavoriteAdapter

    //Other
    private val viewModel: FavoriteViewModel by viewModels()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentFavoriteBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //InitViews
        binding.apply {
            //Load data
            viewModel.favoriteListLiveData.observe(viewLifecycleOwner){
                if (it.isNotEmpty()){
                    tvEmpty.visibility = View.INVISIBLE
                    favoriteList.visibility = View.VISIBLE
                    //InitAdapter
                    favoriteAdapter.setData(it)
                    favoriteList.setupRv(
                        LinearLayoutManager(requireContext()),favoriteAdapter)
                    //Click
                    favoriteAdapter.setOnItemClick { id->
                        val direction = FavoriteFragmentDirections.actionToDetail(id)
                        findNavController().navigate(direction)
                    }
                }else{
                    tvEmpty.visibility = View.VISIBLE
                    favoriteList.visibility = View.INVISIBLE
                }
            }
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}