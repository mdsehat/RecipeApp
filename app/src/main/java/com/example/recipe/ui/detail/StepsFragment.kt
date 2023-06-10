package com.example.recipe.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipe.R
import com.example.recipe.adapter.StepsAdapter
import com.example.recipe.data.model.detail.DetailResponse
import com.example.recipe.databinding.FragmentMenuBinding
import com.example.recipe.databinding.FragmentStepsBinding
import com.example.recipe.utils.COUNT_STEPS
import com.example.recipe.utils.setupRv
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class StepsFragment : BottomSheetDialogFragment() {
    //Binding
    private var _binding: FragmentStepsBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var stepsAdapter: StepsAdapter

    //Other
    private val args: StepsFragmentArgs by navArgs()
    private lateinit var steps: List<DetailResponse.AnalyzedInstruction.Step>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentStepsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //InitViews
        binding.apply {
            args.let {
                steps = it.data.steps!!
                if (steps.isNotEmpty()) {
                    COUNT_STEPS = steps.size
                    stepsAdapter.setData(steps)
                    binding.stepsList.setupRv(LinearLayoutManager(requireContext()), stepsAdapter)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}