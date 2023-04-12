package com.example.recipe.ui.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import coil.load
import com.example.recipe.R
import com.example.recipe.databinding.ActivityMainBinding
import com.example.recipe.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {
    //Binding
    private var _binding : FragmentRegisterBinding?= null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentRegisterBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //InitViews
        binding.apply {
            //LoadImg
            registerLogo.load(R.drawable.register_logo)
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}