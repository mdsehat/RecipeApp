package com.example.recipe.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.recipe.data.database.entity.DetailEntity
import com.example.recipe.data.database.entity.FavoriteEntity
import com.example.recipe.data.model.detail.DetailResponse
import com.example.recipe.data.model.detail.SimilarResponse
import com.example.recipe.data.repository.RecipeRepository
import com.example.recipe.utils.NetworkErrorCode
import com.example.recipe.utils.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val repository: RecipeRepository) : ViewModel() {

    //--API--//
    //Detail
    val detailLiveData = MutableLiveData<NetworkResponse<DetailResponse>>()

    fun callDetail(id:Int, apiKey: String) = viewModelScope.launch {
        detailLiveData.value = NetworkResponse.Loading()
        val response = repository.remote.getDetail(id, apiKey)
        detailLiveData.value = NetworkErrorCode(response).ErrorCode()
        //Cache
        val cache = detailLiveData.value?.data
        if (cache != null) detailOffline(id, cache)
    }

    //Similar
    val similarLiveData = MutableLiveData<NetworkResponse<SimilarResponse>>()

    fun callSimilar(id:Int, apiKey: String) = viewModelScope.launch {
        similarLiveData.value = NetworkResponse.Loading()
        val response = repository.remote.getSimilar(id, apiKey)
        similarLiveData.value = NetworkErrorCode(response).ErrorCode()
    }

    //--Cache--//
    private fun saveDetail(entity:DetailEntity) = viewModelScope.launch {
        repository.local.saveDetail(entity)
    }

    fun loadDetailFromDb(id:Int) = repository.local.loadDetail(id).asLiveData()

    val existsDetailLiveData = MutableLiveData<Boolean>()
    fun existsDetail(id:Int) = viewModelScope.launch {
        repository.local.existsDetail(id).collect{
            existsDetailLiveData.postValue(it)
        }
    }

    fun detailOffline(id:Int, response:DetailResponse){
        val entity = DetailEntity(id, response)
        saveDetail(entity)
    }

    //--Favorite--//
    fun saveFavorite(entity: FavoriteEntity) = viewModelScope.launch {
        repository.local.saveFavorite(entity)
    }
    fun deleteFavorite(entity: FavoriteEntity) = viewModelScope.launch {
        repository.local.deleteFavorite(entity)
    }
    val existsFavoriteLiveData = MutableLiveData<Boolean>()
    fun existsFavorite(id:Int) = viewModelScope.launch {
        repository.local.existsFavorite(id).collect{
            existsFavoriteLiveData.postValue(it)
        }
    }
}