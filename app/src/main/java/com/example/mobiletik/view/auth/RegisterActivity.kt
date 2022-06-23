package com.example.mobiletik.view.auth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.mobiletik.databinding.ActivityRegisterBinding
import com.example.mobiletik.viewmodel.RegisterActivityViewmodel

class RegisterActivity : AppCompatActivity() {

    internal lateinit var binding : ActivityRegisterBinding

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val model = ViewModelProvider(this)[RegisterActivityViewmodel::class.java]

        binding.btnRegister.setOnClickListener {
            model.checkForm(this)
        }

        binding.login.setOnClickListener {
            onBackPressed()
            finish()
        }
    }
}