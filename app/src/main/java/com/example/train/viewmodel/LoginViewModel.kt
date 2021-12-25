package com.example.train.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.train.model.UserDataModel
import com.example.train.remote.Resource
import com.example.train.repo.LoginRepository
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: LoginRepository) : ViewModel() {


    private val _userInfo: MutableLiveData<Resource<UserDataModel>> = MutableLiveData()
    val userInfo: LiveData<Resource<com.example.train.model.UserDataModel>>
        get() = _userInfo


    fun userInfo(username: String) = viewModelScope.launch {
        _userInfo.value=repository.userInfo(username)
    }
}