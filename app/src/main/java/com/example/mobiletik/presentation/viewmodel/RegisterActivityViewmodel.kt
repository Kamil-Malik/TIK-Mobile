package com.example.mobiletik.presentation.viewmodel

import android.util.Patterns
import androidx.lifecycle.ViewModel
import com.example.mobiletik.model.usecase.Authentication
import com.example.mobiletik.model.data.UserInfo
import com.example.mobiletik.presentation.view.RegisterActivity

class RegisterActivityViewmodel : ViewModel() {

    fun checkForm(mActivity : RegisterActivity) {

        val userName = mActivity.binding.userName.text.toString()
        val userNis = mActivity.binding.userNis.text.toString()
        val email = mActivity.binding.userEmail.text.toString()
        val password = mActivity.binding.userPassword.text.toString()

        if (userName.isEmpty()) {
            mActivity.binding.userName.error = "Silahkan masukkan nama anda"
        }
        if (userNis.isEmpty() || userNis.length < 6
        ) {
            mActivity.binding.userNis.error = "Silahkan masukkan NIS sesuai ketentuan"
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches() || email.isEmpty()
        ) {
            mActivity.binding.userEmail.error = "Silahkan masukkan alamat Email yang valid"
        }
        if (password.length < 8 || password.isEmpty()
        ) {
            mActivity.binding.userPassword.error = "Silahkan masukkan password minimal 8 karakter"
        }
        if (
            userName.isNotEmpty() &&
            userNis.isNotEmpty() &&
            userNis.length >= 8 &&
            email.isNotEmpty() &&
            Patterns.EMAIL_ADDRESS.matcher(email).matches() &&
            password.isNotEmpty() &&
            password.length >= 9
        ) {
            val userInfo = UserInfo(userName, userNis, email)
            val auth = Authentication
            auth.signUp(mActivity, email, password, userInfo)
        }
    }


}