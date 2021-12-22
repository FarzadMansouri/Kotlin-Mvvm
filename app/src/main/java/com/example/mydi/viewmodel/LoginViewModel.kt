package com.example.mydi.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mydi.model.UserDataModel
import com.example.mydi.repo.LoginRepository
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: LoginRepository) : ViewModel() {


    private val _userInfo: MutableLiveData<UserDataModel> = MutableLiveData()
    val userInfo: LiveData<UserDataModel> = _userInfo


    fun userInfo(username: String) = viewModelScope.launch {
        repository.userInfo(username)
    }
}