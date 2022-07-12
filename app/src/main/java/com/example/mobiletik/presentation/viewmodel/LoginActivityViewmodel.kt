package com.example.mobiletik.presentation.viewmodel

import android.util.Patterns
import androidx.lifecycle.ViewModel
import com.example.mobiletik.domain.usecase.Authentication
import com.example.mobiletik.presentation.view.LoginActivity

class LoginActivityViewmodel : ViewModel() {

    fun checkForm(mActivity : LoginActivity) {
        val email = mActivity.binding.userEmail.text.toString()
        val password = mActivity.binding.userPassword.text.toString()
        if (email.isEmpty()) {
            mActivity.binding.userEmail.error = "Silahkan masukkan Email terlebih dahulu"
        }
        if (password.isEmpty()) {
            mActivity.binding.userPassword.error = "Silahkan masukkan Password terlebih dahulu"
        } else if (email.isNotEmpty() && !Patterns.EMAIL_ADDRESS.matcher(email).matches()
        ) {
            mActivity.binding.userEmail.error = "Silahkan masukkan Email yang valid"
        } else if (email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email)
                .matches() && password.isNotEmpty()
        ) {
            val auth = Authentication
            auth.signIn(mActivity, email, password)
        }
    }
}