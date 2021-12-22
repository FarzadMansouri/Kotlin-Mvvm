package com.example.mydi.view.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.mydi.databinding.FragmentLoginBinding
import com.example.mydi.remote.ClientApi
import com.example.mydi.repo.LoginRepository
import com.example.mydi.utils.BaseFragment
import com.example.mydi.utils.enable
import com.example.mydi.viewmodel.LoginViewModel

class LoginFragment() : BaseFragment<LoginViewModel, FragmentLoginBinding, LoginRepository>() {
    override fun getViewModel(): Class<LoginViewModel> = LoginViewModel::class.java
    override fun getRepository(): LoginRepository =
        LoginRepository(remoteDataSource.makeApi(ClientApi::class.java))

    override fun fragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentLoginBinding.inflate(inflater, container, false)


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.btnLogin.enable(false)

        binding.textUsername.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.btnLogin.enable(false)
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.btnLogin.enable(!binding.usernameField.editText?.text.isNullOrEmpty())

            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })


        binding.btnLogin.setOnClickListener {
            if (binding.usernameField.editText?.text.isNullOrEmpty()) {
                binding.usernameField.editText?.error = "Enter Username"
            } else {
                binding.btnLogin.enable(true)
                viewModel.userInfo(binding.usernameField.editText?.text.toString())
            }
        }




        viewModel.userInfo.observe(viewLifecycleOwner,{
            Log.d("TAG", "onActivityCreated: $it" )
        })

    }

}