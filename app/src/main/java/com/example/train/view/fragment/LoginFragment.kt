package com.example.train.view.fragment

import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.train.databinding.FragmentLoginBinding
import com.example.train.remote.Resource
import com.example.train.repo.LoginRepository
import com.example.train.utils.BaseFragment
import com.example.train.utils.enable
import com.example.train.utils.visible
import com.example.train.viewmodel.LoginViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class LoginFragment() : BaseFragment<LoginViewModel, FragmentLoginBinding, LoginRepository>() {
    override fun getViewModel(): Class<LoginViewModel> = LoginViewModel::class.java
    override fun getRepository(): LoginRepository =
        LoginRepository(remoteDataSource.makeApi(com.example.train.remote.ClientApi::class.java))

    companion object{
        private const val TAG = "LoginFragment"
    }
    
    override fun fragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentLoginBinding.inflate(inflater, container, false)


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.btnLogin.enable(false)
        binding.loadingLogin.visible(false)

        binding.textUsername.addTextChangedListener(object : TextWatcher {
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
                binding.loadingLogin.visible(true)
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

                    lifecycleScope.launch {
                        requireContext().userDataStore.updateData { store ->
                            store.toBuilder()
                                .setName(it.value.name)
                                .setId(it.value.id)
                                .build()

                        }
                    }

                    binding.textUserInfo.text =
                        "${it.value.bio} \t\n ${it.value.company} \t\n ${it.value.followers} \t\n ${it.value.id} \t\n ${it.value.name} \t\n ${it.value.location} \t\n ${it.value.site_admin}"


                    Handler().postDelayed({
                        lifecycleScope.launch(Dispatchers.IO) {
                            requireContext().userDataStore.data.map {

                                Log.d(TAG, "onActivityCreated: ${it.name } ${it.id}")
                            }
                        }
                    }, 5000)


                }
                is Resource.Failure -> {
                    Log.d("TAG", "onActivityCreated: ERROR in load User Info ")
                }
            }

        })





    }

}