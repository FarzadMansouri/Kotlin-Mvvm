package com.example.train.utils

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.example.train.UserStore
import com.example.train.model.UserDataModel
import com.example.train.repo.BaseRepository
import com.example.train.viewmodel.ViewModelFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

abstract class BaseFragment<VM : ViewModel, B : ViewBinding, R : BaseRepository> : Fragment() {

    protected lateinit var binding: B
    protected val remoteDataSource = com.example.train.remote.RemoteDataSource()
    protected lateinit var viewModel: VM

    protected val Context.userDataStore: DataStore<UserStore> by dataStore(
        AppContainer.USER_DATA_STORE_FILE_NAME,
        UserSerializer
    )
    protected val Context.userPreferencesStore: DataStore<Preferences> by preferencesDataStore(
        AppContainer.USER_PREFERENCES_NAME
    )

    fun saveProtoData(value: UserDataModel) {
        lifecycleScope.launch {
            requireContext().userDataStore.updateData {
                it.toBuilder()
                    .setName(value.login)
                    .setId(value.id)
                    .build()
            }
        }
    }

    fun readProtoType() {
        val userFlow: Flow<UserStore> = requireContext().userDataStore.data.map {
            it
        }
        Handler(Looper.getMainLooper()).postDelayed({
            lifecycleScope.launch {
                userFlow.collect {
                    Toast.makeText(
                        requireContext(),
                        "Proto DataStore: ${it.name}",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }
        }, 3000)
    }

    fun saveUserPreferences(value: String) {
        lifecycleScope.launch {
            requireContext().userPreferencesStore.edit {
                it[PreferencesKeys.USER_NAME] = value
            }
        }
    }

    fun readUserPreferences() {
        val userFlow: Flow<String> = requireContext().userPreferencesStore.data
            .map { preferences ->
                preferences[PreferencesKeys.USER_NAME].toString()
            }
        Handler(Looper.getMainLooper()).postDelayed({
            lifecycleScope.launch {
                userFlow.collect {
                    Toast.makeText(
                        requireContext(),
                        "Preferences DataStore: $it",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }
        }, 300)

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = fragmentBinding(inflater, container)
        val factory = ViewModelFactory(getRepository())
        viewModel = ViewModelProvider(this, factory).get(getViewModel())


        return binding.root
    }


    abstract fun getViewModel(): Class<VM>
    abstract fun getRepository(): R
    abstract fun fragmentBinding(inflater: LayoutInflater, container: ViewGroup?): B

}