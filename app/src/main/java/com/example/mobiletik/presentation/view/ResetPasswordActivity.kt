package com.example.mobiletik.presentation.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mobiletik.databinding.ActivityResetPasswordBinding
import com.example.mobiletik.domain.usecase.ResetPassword
import com.example.mobiletik.domain.usecase.ResetPasswordValidation.validate

class ResetPasswordActivity : AppCompatActivity() {

    internal lateinit var binding: ActivityResetPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        with(binding) {
            btnReset.setOnClickListener {
                val email = userEmail.text.toString()
                val validation = validate(email)
                if (validation.result) {
                    ResetPassword.sendResetPasswordLink(this@ResetPasswordActivity, email)
                } else {
                    Toast.makeText(
                        this@ResetPasswordActivity,
                        validation.errorMessage,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            daftar.setOnClickListener {
                Intent(this@ResetPasswordActivity, RegisterActivity::class.java).also {
                    startActivity(it)
                    finish()
                }
            }
        }
    }
}