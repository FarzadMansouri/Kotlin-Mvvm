package com.example.train.view.fragment

import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.train.UserStore
import com.example.train.databinding.FragmentLoginBinding
import com.example.train.remote.Resource
import com.example.train.repo.LoginRepository
import com.example.train.utils.BaseFragment
import com.example.train.utils.PreferencesKeys
import com.example.train.utils.enable
import com.example.train.utils.visible
import com.example.train.viewmodel.LoginViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.internal.notifyAll

class LoginFragment() : BaseFragment<LoginViewModel, FragmentLoginBinding, LoginRepository>() {
    override fun getViewModel(): Class<LoginViewModel> = LoginViewModel::class.java
    override fun getRepository(): LoginRepository =
        LoginRepository(remoteDataSource.makeApi(com.example.train.remote.ClientApi::class.java))
    private var isProto=false
    private var isPrefs=false

    companion object {
        private const val TAG = "LoginFragment"
    }

    override fun fragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentLoginBinding.inflate(inflater, container, false)


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.btnProtoType.enable(false)
        binding.btnPreferences.enable(false)
        binding.loadingLogin.visible(false)

        binding.textUsername.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.btnProtoType.enable(false)
                binding.btnPreferences.enable(false)
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.btnProtoType.enable(!binding.usernameField.editText?.text.isNullOrEmpty())
                binding.btnPreferences.enable(!binding.usernameField.editText?.text.isNullOrEmpty())

            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })


        binding.btnProtoType.setOnClickListener {
            if (binding.usernameField.editText?.text.isNullOrEmpty()) {
                binding.usernameField.editText?.error = "Enter Username"
            } else {
                binding.btnProtoType.enable(true)
                binding.loadingLogin.visible(true)
                isProto=true
                isPrefs=false
                viewModel.userInfo(binding.usernameField.editText?.text.toString())
            }
        }
        binding.btnPreferences.setOnClickListener {
            if (binding.usernameField.editText?.text.isNullOrEmpty()) {
                binding.usernameField.editText?.error = "Enter Username"
            } else {
                binding.btnPreferences.enable(true)
                binding.loadingLogin.visible(true)
                isProto=false
                isPrefs=true
                viewModel.userInfo(binding.usernameField.editText?.text.toString())
            }
        }


        viewModel.userInfo.observe(viewLifecycleOwner, {
            Log.d("TAG", "onActivityCreated:")

            when (it) {
                is Resource.Success -> {
                    binding.loadingLogin.visible(false)
                    Glide.with(this)
                        .load(it.value.avatar_url)
                        .circleCrop()
                        .into(binding.userImageView)

  binding.textUserInfo.text =
  "${it.value.bio} \t\n ${it.value.company} \t\n  \t\n ${it.value.id} \t\n ${it.value.login} \t\n ${it.value.location} "


                    if(isProto){
                        saveProtoData(it.value)
                        readProtoType()
                    }
                    if(isPrefs){
                        saveUserPreferences(it.value.login)
                    readUserPreferences()
                    }

                }
                is Resource.Failure -> {
                    Log.d("TAG", "onActivityCreated: ERROR in load User Info ")
                }
            }

        })


    }


}