package com.example.recipe.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.recipe.data.database.entity.RecipeEntity
import com.example.recipe.data.model.recipe.ResponseRecipe
import com.example.recipe.data.repository.MenuRepository
import com.example.recipe.data.repository.RecipeRepository
import com.example.recipe.utils.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(
    private val repository: RecipeRepository,
    private val menuRepository: MenuRepository
) : ViewModel() {
    //--Popular--//
    //Queries
    fun popularQueries(): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()
        queries[API_KEY] = API_KEY_NUMBER
        queries[SORT] = POPULARITY
        queries[NUMBER] = MIN_COUNT.toString()
        queries[RECIPE_INFORMATION] = "true"
        return queries
    }

    //Api
    val popularLiveData = MutableLiveData<NetworkResponse<ResponseRecipe>>()

    fun callPopular(map: Map<String, String>) = viewModelScope.launch {
        popularLiveData.value = NetworkResponse.Loading()
        try {
            val response = repository.remote.getRecipe(map)
            popularLiveData.value = NetworkErrorCode(response).ErrorCode()
            //Cache
            val cache = popularLiveData.value!!.data
            if (cache != null) cachingPopular(cache)
        }catch (e: Exception){
            popularLiveData.value = NetworkResponse.Error("Connection error!")
        }
    }

    //Local
    private fun savePopular(entity: RecipeEntity) = viewModelScope.launch(IO) {
        repository.local.saveRecipe(entity)
    }

    val readPopularFromDb = repository.local.loadRecipe().asLiveData()

    private fun cachingPopular(response: ResponseRecipe) {
        val entity = RecipeEntity(0, response)
        savePopular(entity)
    }

    //--Recent--//
    //Queries
    private val TAG ="mds"
    private var meal = MAIN_COURSE
    private var diet = GLUTEN_FREE
    fun recentQueries(): HashMap<String, String> {
        viewModelScope.launch {
            menuRepository.readMenuData.collect{
                meal = it.mealTitle
                diet = it.dietTitle
            }
        }
        val queries: HashMap<String, String> = HashMap()
        queries[API_KEY] = API_KEY_NUMBER
        queries[TYPE] = meal
        queries[DIET] = diet
        queries[NUMBER] = MAX_COUNT.toString()
        queries[RECIPE_INFORMATION] = "true"
        return queries
    }

    //Api
    val recentLiveData = MutableLiveData<NetworkResponse<ResponseRecipe>>()

    fun callRecent(map: Map<String, String>) = viewModelScope.launch {
        recentLiveData.value = NetworkResponse.Loading()
        try {
            val response = repository.remote.getRecipe(map)
            recentLiveData.value = handlingResponseRecent(response)
            //Cache
            val cache = recentLiveData.value!!.data
            if (cache != null) cachingRecent(cache)
        }catch (e: Exception){
            popularLiveData.value = NetworkResponse.Error("Connection error!")
        }
    }

    private fun handlingResponseRecent(response: Response<ResponseRecipe>): NetworkResponse<ResponseRecipe> {
        return when {
            response.message().contains("timeout") -> NetworkResponse.Error("Timeout")
            response.code() == 401 -> NetworkResponse.Error("You are not authorized.")
            response.code() == 402 -> NetworkResponse.Error("Your free plan finished!")
            response.code() == 500 -> NetworkResponse.Error("Try again")
            response.body()!!.results!!.isEmpty() -> NetworkResponse.Error("Not found any items.")
            response.isSuccessful -> NetworkResponse.Success(response.body()!!)
            !response.isSuccessful -> NetworkResponse.Error("Please check your network!")
            else -> NetworkResponse.Error(response.message())
        }
    }

    //Local
    private fun saveRecent(entity: RecipeEntity) = viewModelScope.launch(IO) {
        repository.local.saveRecipe(entity)
    }

    val readRecentFromDb = repository.local.loadRecipe().asLiveData()

    private fun cachingRecent(response: ResponseRecipe) {
        val entity = RecipeEntity(1, response)
        saveRecent(entity)
    }
}