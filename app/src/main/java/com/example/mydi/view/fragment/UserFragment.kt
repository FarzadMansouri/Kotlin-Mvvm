package com.example.mydi.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mydi.databinding.FragmentUserBinding
import com.example.mydi.repo.UserRepository
import com.example.mydi.utils.BaseFragment
import com.example.mydi.viewmodel.UserViewModel

class UserFragment : BaseFragment<UserViewModel, FragmentUserBinding, UserRepository>() {
    override fun getViewModel(): Class<UserViewModel> = UserViewModel::class.java

    override fun getRepository(): UserRepository = UserRepository()

    override fun fragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentUserBinding.inflate(inflater, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }

}