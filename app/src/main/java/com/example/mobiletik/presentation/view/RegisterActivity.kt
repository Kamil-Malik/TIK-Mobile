package com.example.mobiletik.presentation.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mobiletik.databinding.ActivityRegisterBinding
import com.example.mobiletik.domain.usecase.Register
import com.example.mobiletik.domain.usecase.RegisterFormValidation.validate

class RegisterActivity : AppCompatActivity() {

    internal lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            btnRegister.setOnClickListener {
                val userName = userName.text.toString()
                val userNIS = userNis.text.toString()
                val email = userEmail.text.toString()
                val kelas = userKelas.text.toString().uppercase()
                val password = userPassword.text.toString()
                val validation = validate(userName, userNIS, email, kelas, password)
                if (validation.result) {
                    Register.signUpWithEmailAndPassword(
                        this@RegisterActivity,
                        email,
                        password,
                        userName,
                        userNIS,
                        kelas
                    )
                } else {
                    with(validation) {
                        if (!this.errorMessageNama.isNullOrEmpty()) {
                            binding.userName.error = this.errorMessageNama
                        }
                        if (!this.errorMessageNIS.isNullOrEmpty()) {
                            binding.userNis.error = this.errorMessageNIS
                        }
                        if (!this.errorMessageKelas.isNullOrEmpty()) {
                            binding.userKelas.error = this.errorMessageKelas
                        }
                        if (!this.errorMessageEmail.isNullOrEmpty()) {
                            binding.userEmail.error = this.errorMessageEmail
                        }
                        if (!this.errorMessagePassword.isNullOrEmpty()) {
                            binding.userPassword.error = this.errorMessagePassword
                        }
                    }
                }
            }

            login.setOnClickListener {
                onBackPressed()
                finish()
            }
        }
    }
}