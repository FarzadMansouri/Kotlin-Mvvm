package com.example.mydi.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mydi.viewmodel.LoginViewModel


  open class ViewModelFactory: ViewModelProvider.Factory {
      override fun <T : ViewModel?> create(modelClass: Class<T>): T {

          return when(modelClass){
            is   modelClass.isAssignableFrom(LoginViewModel::class.java):T
          }
      }

  }
