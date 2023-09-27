package com.example.recipe.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipe.data.model.lucky.ResponseLucky
import com.example.recipe.data.model.recipe.ResponseRecipe
import com.example.recipe.data.repository.RandomRepository
import com.example.recipe.utils.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RandomViewModel @Inject constructor(private val repository: RandomRepository): ViewModel() {
    //Queries
    fun luckyQueries(): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()
        queries[API_KEY] = API_KEY_NUMBER
        queries[NUMBER] = "1"
        queries[RECIPE_INFORMATION] = "true"
        return queries
    }

    //Api
    val luckyLiveData = MutableLiveData<NetworkResponse<ResponseLucky>>()

    fun callLucky(map: Map<String, String>) = viewModelScope.launch {
        luckyLiveData.value = NetworkResponse.Loading()
        try {
            val response = repository.remote.getRandom(map)
            luckyLiveData.value = NetworkErrorCode(response).ErrorCode()
        }catch (e: Exception){
            luckyLiveData.value = NetworkResponse.Error("Connection error!")
        }

    }
}