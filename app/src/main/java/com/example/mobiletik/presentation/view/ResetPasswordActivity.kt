package com.example.mobiletik.presentation.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.mobiletik.databinding.ActivityResetPasswordBinding
import com.example.mobiletik.presentation.viewmodel.ResetPasswordActivityViewmodel

class ResetPasswordActivity : AppCompatActivity() {

    internal lateinit var binding : ActivityResetPasswordBinding

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val model = ViewModelProvider(this)[ResetPasswordActivityViewmodel::class.java]

        binding.btnReset.setOnClickListener {
            model.checkForm(this)
        }

        binding.daftar.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }
    }
}