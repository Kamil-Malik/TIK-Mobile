package com.example.mobiletik.presentation.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.mobiletik.databinding.ActivityLoginBinding
import com.example.mobiletik.domain.usecase.Authentication
import com.example.mobiletik.presentation.viewmodel.LoginActivityViewmodel

class LoginActivity : AppCompatActivity() {

    internal lateinit var binding : ActivityLoginBinding

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val model : LoginActivityViewmodel =
            ViewModelProvider(this)[LoginActivityViewmodel::class.java]
        val auth = Authentication
        auth.checkUser(this)

        binding.btnLogin.setOnClickListener {
            model.checkForm(this)
        }

        binding.resetPassword.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    ResetPasswordActivity::class.java
                )
            )
        }

        binding.daftar.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    RegisterActivity::class.java
                )
            )
        }
    }
}