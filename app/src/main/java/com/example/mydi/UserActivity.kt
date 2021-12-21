package com.example.mydi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mydi.viewmodel.LoginViewModel

class UserActivity : AppCompatActivity() {

    private lateinit var userViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}