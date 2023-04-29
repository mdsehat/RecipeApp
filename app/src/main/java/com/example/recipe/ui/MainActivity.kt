package com.example.recipe.ui

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.recipe.R
import com.example.recipe.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import io.github.inflationx.viewpump.ViewPumpContextWrapper

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    //Binding
    private var _binding : ActivityMainBinding ?= null
    private val binding get() = _binding!!

    private lateinit var navHost: NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //Navigation
        navHost = supportFragmentManager.findFragmentById(R.id.navHostMain) as NavHostFragment
        binding.apply {
            mainBottomNav.setupWithNavController(navHost.navController)
            mainBottomNav.background = null
        }
        //Gone bottom navigation
        navHost.navController.addOnDestinationChangedListener{_,destination,_ ->
            when(destination.id){
                R.id.splashFragment -> visibilityBottomNav(false)
                R.id.registerFragment -> visibilityBottomNav(false)
                else -> visibilityBottomNav(true)
            }
        }
    }
    private fun visibilityBottomNav(visibility: Boolean){
        binding.apply {
            if (visibility){
                mainBottomApp.isVisible = true
                mainFab.isVisible = true
            }else{
                mainBottomApp.isVisible = false
                mainFab.isVisible = false
            }
        }
    }
    //Calligraphy
    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase))
    }
    //Navigation
    override fun onNavigateUp(): Boolean {
        return navHost.navController.navigateUp() ||super.onNavigateUp()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}