package com.example.recipe.ui.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import coil.load
import com.example.recipe.R
import com.example.recipe.data.model.register.BodyRegister
import com.example.recipe.databinding.ActivityMainBinding
import com.example.recipe.databinding.FragmentRegisterBinding
import com.example.recipe.utils.API_KEY
import com.example.recipe.utils.NetworkChecker
import com.example.recipe.utils.NetworkResponse
import com.example.recipe.utils.makeSnackBar
import com.example.recipe.viewmodel.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import javax.inject.Inject
@AndroidEntryPoint
class RegisterFragment : Fragment() {
    //Binding
    private var _binding : FragmentRegisterBinding?= null
    private val binding get() = _binding!!

    @Inject
    lateinit var body: BodyRegister

    @Inject
    lateinit var networkChecker: NetworkChecker

    //Other
    private val viewModel: RegisterViewModel by viewModels()
    private var email = ""


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
            //Email
            EmailEdt.addTextChangedListener {
                if (it.toString().contains("@") && it.toString().contains(".com")){
                    EmailLay.error = ""
                    email = it.toString()
                }else{
                    EmailLay.error = getString(R.string.emailNotValid)
                }
            }
            //Button
            btnRegister.setOnClickListener {
                //Get information
                val firstName = firstNameEdt.text.toString()
                val lastName = LastNameEdt.text.toString()
                val username = UserNameEdt.text.toString()
                //Body
                body = BodyRegister(email, firstName, lastName, username)
                //Check network
                lifecycleScope.launchWhenStarted {
                    networkChecker.checkNetworkAvailability().collect{ state->
                        if (state){
                            //Call api
                            viewModel.callRegister(API_KEY, body)
                        }else{
                            root.makeSnackBar(getString(R.string.checkConnection))
                        }

                    }
                }

            }
            //Load response api
            loadCallRegister()
        }
    }

    private fun loadCallRegister(){
        viewModel.registerLiveData.observe(viewLifecycleOwner){ response->
            when(response){
                is NetworkResponse.Loading ->{}
                is NetworkResponse.Success ->{
                    response.data?.let { data->
                        viewModel.saveData(data.username.toString(), data.hash.toString())
                        findNavController().apply {
                            popBackStack(R.id.registerFragment, true)
                            navigate(R.id.actionToRecipe)
                        }
                    }
                }
                is NetworkResponse.Error ->{
                    binding.root.makeSnackBar(response.message.toString())
                }
            }
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}