package com.example.train.utils

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.example.train.UserStore

import com.example.train.repo.BaseRepository
import com.example.train.viewmodel.ViewModelFactory

abstract class BaseFragment<VM : ViewModel, B : ViewBinding, R : BaseRepository> : Fragment() {

    protected lateinit var binding: B
    protected val remoteDataSource = com.example.train.remote.RemoteDataSource()
    protected lateinit var viewModel: VM
    protected val Context.userDataStore : DataStore<UserStore> by dataStore(AppContainer.USER_DATA_STORE_FILE_NAME,UserSerializer)



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = fragmentBinding(inflater, container)
        val factory = ViewModelFactory(getRepository())
        viewModel=ViewModelProvider(this,factory).get(getViewModel())


        return binding.root
    }


    abstract fun getViewModel(): Class<VM>
    abstract fun getRepository(): R
    abstract fun fragmentBinding(inflater: LayoutInflater, container: ViewGroup?): B

}