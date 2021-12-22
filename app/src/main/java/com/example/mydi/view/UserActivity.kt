package com.example.mydi.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mydi.R
import com.example.mydi.viewmodel.LoginViewModel

class UserActivity : AppCompatActivity() {

    private lateinit var userViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }
}