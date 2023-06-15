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
class SearchViewModel @Inject constructor(private val repository: RandomRepository): ViewModel() {
    //Queries
    fun searchQueries(search: String): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()
        queries[API_KEY] = API_KEY_NUMBER
        queries[QUERY] = search
        queries[NUMBER] = MAX_COUNT.toString()
        queries[RECIPE_INFORMATION] = "true"
        return queries
    }

    //Api
    val searchLiveData = MutableLiveData<NetworkResponse<ResponseRecipe>>()

    fun callSearch(map: Map<String, String>) = viewModelScope.launch {
        searchLiveData.value = NetworkResponse.Loading()
        val response = repository.remote.getRecipe(map)
        searchLiveData.value = NetworkErrorCode(response).ErrorCode()
    }
}