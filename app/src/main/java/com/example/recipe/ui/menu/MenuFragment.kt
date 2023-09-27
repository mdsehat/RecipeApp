package com.example.recipe.ui.menu

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.example.recipe.R
import com.example.recipe.databinding.FragmentMenuBinding
import com.example.recipe.utils.singleObserve
import com.example.recipe.viewmodel.MenuViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import com.google.android.material.chip.ChipGroup
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MenuFragment : BottomSheetDialogFragment() {

    //Binding
    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!

    private val viewModel:MenuViewModel by viewModels()
    private var chipCounter = 1
    private var mealTitle = ""
    private var dietTitle = ""
    private var mealId = 0
    private var dietId = 0
    private var firstId = 0
    private val TAG = "menuMy"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentMenuBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //InitViews
        binding.apply {
            //Init chips
            setupChip(viewModel.typeList(), mealGroup)
            setupChip(viewModel.dietList(), dietGroup)
            //Read data from datastore
            viewModel.readDataFromDatastore.asLiveData().observe(viewLifecycleOwner){
                mealTitle = it.mealTitle
                dietTitle = it.dietTitle
                updateChip(it.mealId, mealGroup)
                updateChip(it.dietId, dietGroup)
            }
            //MealChip - onClick
            mealGroup.setOnCheckedStateChangeListener{group, checkedIds->
                var chip:Chip
                checkedIds.forEach {
                    chip = group.findViewById(it)
                    mealTitle = chip.text.toString().lowercase()
                    mealId = it
                }
            }
            //DietChip - onClick
            dietGroup.setOnCheckedStateChangeListener{group, checkedIds->
                var chip:Chip
                checkedIds.forEach {
                    chip = group.findViewById(it)
                    dietTitle = chip.text.toString().lowercase()
                    dietId = it
                }
            }
            //Submit
            btnSubmit.setOnClickListener {
                viewModel.saveData(mealTitle, mealId, dietTitle, dietId)
                val direction = MenuFragmentDirections.actionMenuToRecipe().setIsUpdated(true)
                findNavController().navigate(direction)
            }
        }

    }

    private fun updateChip(id: Int, view: ChipGroup) {
        if (id != 0){
            view.findViewById<Chip>(id).isChecked = true
        }
    }

    private fun setupChip(list:MutableList<String>, view: ChipGroup){
        list.forEach {
            val chip = Chip(requireContext())
            val drawable = ChipDrawable.createFromAttributes(requireContext(), null, 0, R.style.darkChip)
            chip.setChipDrawable(drawable)
            chip.setTextColor(ContextCompat.getColor(requireContext(),R.color.white))
            chip.text = it
            chip.id = chipCounter++
            view.addView(chip)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}