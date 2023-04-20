package com.example.recipe.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipe.data.model.register.BodyRegister
import com.example.recipe.data.model.register.ResponseRegister
import com.example.recipe.data.repository.RegisterRepository
import com.example.recipe.utils.NetworkErrorCode
import com.example.recipe.utils.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val repository: RegisterRepository): ViewModel() {

    //Register api
    val registerLiveData = MutableLiveData<NetworkResponse<ResponseRegister>>()
    fun callRegister(apiKey: String, body: BodyRegister) = viewModelScope.launch {
        //Loading
        registerLiveData.value = NetworkResponse.Loading()
        //getData
        val response = repository.postRegister(apiKey, body)
        registerLiveData.value = NetworkErrorCode(response).ErrorCode()
    }

    //DataStore
    fun saveData(username:String, hash:String) = viewModelScope.launch {
        repository.saveDataRegister(username, hash)
    }

    fun readData() = repository.readDataRegister
}