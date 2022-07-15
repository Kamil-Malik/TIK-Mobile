package com.example.mobiletik.presentation.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mobiletik.databinding.ActivityRegisterBinding
import com.example.mobiletik.domain.usecase.Register
import com.example.mobiletik.domain.usecase.RegisterFormValidation.validate

class RegisterActivity : AppCompatActivity() {

    internal lateinit var binding : ActivityRegisterBinding

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            btnRegister.setOnClickListener {
                val userName = userName.text.toString()
                val userNIS = userNis.text.toString()
                val email = userEmail.text.toString()
                val password = userPassword.text.toString()
                val validation = validate(userName, userNIS, email, password)
                if(validation.result){
                    Register.signUpWithEmailAndPassword(this@RegisterActivity, email, password, userName, userNIS)
                } else {
                    Toast.makeText(this@RegisterActivity, validation.errorMessage, Toast.LENGTH_SHORT).show()
                }
            }

            login.setOnClickListener {
                onBackPressed()
                finish()
            }
        }
    }
}