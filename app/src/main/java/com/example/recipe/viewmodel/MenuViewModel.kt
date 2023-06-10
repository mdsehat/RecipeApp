package com.example.recipe.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipe.data.repository.MenuRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(private val repository: MenuRepository): ViewModel() {

    fun typeList() = mutableListOf("Main course","Bread","Marinade","Side dish","Breakfast","Fingerfood",
        "Dessert","Soup","Snack","Appetizer","Beverage","Drink","Salad","Sauce")

    fun dietList() = mutableListOf("Gluten Free","Ketogenic","Vegetarian","Vegan","Pescetarian","Paleo","Primal")

    fun saveData(mealTitle: String, mealId: Int, dietTitle: String, dietId: Int) = viewModelScope.launch(IO) {
        repository.saveMenuData(mealTitle, mealId, dietTitle, dietId)
    }
    val readDataFromDatastore = repository.readMenuData
}