package com.example.mobiletik.presentation.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mobiletik.databinding.ActivityLoginBinding
import com.example.mobiletik.domain.usecase.CheckUser.checkLogin
import com.example.mobiletik.domain.usecase.Login.login
import com.example.mobiletik.domain.usecase.LoginFormValidation.validate

class LoginActivity : AppCompatActivity() {

    internal lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        checkLogin(this)
        with(binding) {
            btnLogin.setOnClickListener {
                val email = binding.userEmail.text.toString()
                val pass = binding.userPassword.text.toString()
                val validation = validate(email, pass)
                if (validation.result) {
                    login(this@LoginActivity, email, pass)
                } else {
                    if (!validation.errorMessageEmail.isNullOrEmpty()) {
                        binding.userEmail.error = validation.errorMessageEmail
                    }
                    if (!validation.errorMessagePassword.isNullOrEmpty()) {
                        binding.userPassword.error = validation.errorMessagePassword
                    }
                }
            }

            resetPassword.setOnClickListener {
                startActivity(
                    Intent(
                        this@LoginActivity,
                        ResetPasswordActivity::class.java
                    )
                )
            }

            daftar.setOnClickListener {
                startActivity(
                    Intent(
                        this@LoginActivity,
                        RegisterActivity::class.java
                    )
                )
            }
        }
    }
}