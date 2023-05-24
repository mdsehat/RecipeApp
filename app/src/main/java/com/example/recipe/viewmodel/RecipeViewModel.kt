package com.example.recipe.viewmodel

import android.net.NetworkRequest
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.recipe.data.database.RecipeEntity
import com.example.recipe.data.model.recipe.ResponseRecipe
import com.example.recipe.data.repository.RecipeRepository
import com.example.recipe.utils.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(private val repository: RecipeRepository) : ViewModel() {
    //--Popular--//
    //Queries
    fun popularQueries(): HashMap<String, String>{
        val queries: HashMap<String,String> = HashMap()
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
        val response = repository.remote.getRecipe(map)
        popularLiveData.value = NetworkErrorCode(response).ErrorCode()
        //Cache
        val cache = popularLiveData.value!!.data
        if (cache != null) cachingPopular(cache)
    }

    //Local
    private fun savePopular(entity: RecipeEntity) = viewModelScope.launch(IO) {
        repository.local.saveItem(entity)
    }

    val readPopularFromDb = repository.local.loadItems().asLiveData()

    private fun cachingPopular(response: ResponseRecipe){
        val entity = RecipeEntity(0, response)
        savePopular(entity)
    }

    //--Recent--//
    //Queries
    fun recentQueries(): HashMap<String, String>{
        val queries: HashMap<String,String> = HashMap()
        queries[API_KEY] = API_KEY_NUMBER
        queries[TYPE] = MAIN_COURSE
        queries[DIET] = GLUTEN_FREE
        queries[NUMBER] = MAX_COUNT.toString()
        queries[RECIPE_INFORMATION] = "true"
        return queries
    }
    //Api
    val recentLiveData = MutableLiveData<NetworkResponse<ResponseRecipe>>()

    fun callRecent(map: Map<String, String>) = viewModelScope.launch {
        recentLiveData.value = NetworkResponse.Loading()
        val response = repository.remote.getRecipe(map)
        recentLiveData.value = handlingResponseRecent(response)
        //Cache
        val cache = recentLiveData.value!!.data
        if (cache != null) cachingRecent(cache)
    }
    private fun handlingResponseRecent(response: Response<ResponseRecipe>) : NetworkResponse<ResponseRecipe>{
        return when{
            response.message().contains("timeout") -> NetworkResponse.Error("Timeout")
            response.code() == 401 -> NetworkResponse.Error("You are not authorized.")
            response.code() == 402 -> NetworkResponse.Error("Your free plan finished!")
            response.code() == 500 -> NetworkResponse.Error("Try again")
            response.body()!!.results!!.isEmpty()-> NetworkResponse.Error("Not found any items.")
            response.isSuccessful -> NetworkResponse.Success(response.body()!!)
            else -> NetworkResponse.Error(response.message())
        }
    }

    //Local
    private fun saveRecent(entity: RecipeEntity) = viewModelScope.launch(IO) {
        repository.local.saveItem(entity)
    }

    val readRecentFromDb = repository.local.loadItems().asLiveData()

    private fun cachingRecent(response: ResponseRecipe){
        val entity = RecipeEntity(1, response)
        savePopular(entity)
    }
}