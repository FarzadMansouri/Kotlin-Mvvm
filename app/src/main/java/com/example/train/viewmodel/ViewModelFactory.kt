package com.example.train.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.train.repo.BaseRepository
import com.example.train.repo.LoginRepository

class ViewModelFactory(
    private val repository: BaseRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
       return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> LoginViewModel(repository as LoginRepository) as T
            else -> throw IllegalArgumentException("ViewModel Exception")
        }
    }


}