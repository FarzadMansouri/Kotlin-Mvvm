package com.example.mydi.utils

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.example.mydi.remote.RemoteDataSource
import com.example.mydi.repo.BaseRepository
import com.example.mydi.viewmodel.ViewModelFactory

abstract class BaseFragment<VM : ViewModel, B : ViewBinding, R : BaseRepository> : Fragment() {

    protected lateinit var binding: B
    protected val remoteDataSource = RemoteDataSource()
    protected lateinit var viewModel: VM

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